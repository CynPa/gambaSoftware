/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.Producto;
/*   5:    */ import com.asinfo.as2.entities.ProductoBodega;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.math.BigDecimal;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Map.Entry;
/*  15:    */ import java.util.Set;
/*  16:    */ import javax.ejb.Stateless;
/*  17:    */ import javax.persistence.EntityManager;
/*  18:    */ import javax.persistence.Query;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class ProductoBodegaDao
/*  22:    */   extends AbstractDaoAS2<ProductoBodega>
/*  23:    */ {
/*  24:    */   public ProductoBodegaDao()
/*  25:    */   {
/*  26: 71 */     super(ProductoBodega.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List<ProductoBodega> getListaProductoBodegaVacios(Producto producto)
/*  30:    */   {
/*  31: 75 */     int idOrganizacion = producto.getIdOrganizacion();
/*  32: 76 */     StringBuilder sbSQL = new StringBuilder("SELECT bg FROM Bodega bg");
/*  33: 77 */     sbSQL.append(" WHERE bg.idOrganizacion = :idOrganizacion");
/*  34: 78 */     sbSQL.append(" AND bg.idBodega NOT IN");
/*  35: 79 */     sbSQL.append(" (SELECT pb.bodega.idBodega FROM ProductoBodega pb ");
/*  36: 80 */     sbSQL.append(" WHERE pb.producto.idProducto=:idProducto)");
/*  37:    */     
/*  38: 82 */     Query query = this.em.createQuery(sbSQL.toString());
/*  39: 83 */     query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/*  40: 84 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  41:    */     
/*  42: 86 */     List<Bodega> listaBodega = query.getResultList();
/*  43: 87 */     System.out.println("lista:" + listaBodega.size());
/*  44: 88 */     List<ProductoBodega> listaProductoBodega = new ArrayList();
/*  45: 89 */     for (Bodega bodega : listaBodega)
/*  46:    */     {
/*  47: 90 */       ProductoBodega productoBodega = new ProductoBodega();
/*  48: 91 */       productoBodega.setBodega(bodega);
/*  49: 92 */       productoBodega.setProducto(producto);
/*  50: 93 */       productoBodega.setSaldoMinimo(new BigDecimal(0.0D));
/*  51: 94 */       productoBodega.setSaldoMaximo(new BigDecimal(0.0D));
/*  52: 95 */       listaProductoBodega.add(productoBodega);
/*  53:    */     }
/*  54: 98 */     return listaProductoBodega;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List getReporteSaldosMinimos(int idOrganizacion, int idCategoria, int idSubcategoria, Bodega bodega, List<Bodega> listaBodega, boolean indicadorBajoSaldoMinimo, String valorAtributo)
/*  58:    */   {
/*  59:104 */     Map<String, Object[]> mapa = new HashMap();
/*  60:    */     
/*  61:    */ 
/*  62:    */ 
/*  63:108 */     StringBuilder sbSQL0 = new StringBuilder();
/*  64:109 */     sbSQL0.append(" SELECT pro.idProducto, bo.idBodega, pro.codigo, pro.nombre , sbcat.nombre , bo.nombre, pb.saldoMinimo, pb.saldoMaximo ");
/*  65:110 */     sbSQL0.append(" FROM ProductoBodega pb ");
/*  66:111 */     sbSQL0.append(" INNER JOIN pb.bodega bo ");
/*  67:112 */     sbSQL0.append(" INNER JOIN pb.producto pro ");
/*  68:113 */     sbSQL0.append(" INNER JOIN pro.subcategoriaProducto sbcat ");
/*  69:114 */     sbSQL0.append(" INNER JOIN sbcat.categoriaProducto cat ");
/*  70:116 */     if (valorAtributo != "") {
/*  71:117 */       sbSQL0.append(" INNER JOIN pro.listaProductoAtributo pa");
/*  72:    */     }
/*  73:119 */     sbSQL0.append(" WHERE pb.idOrganizacion = :idOrganizacion");
/*  74:120 */     if (idCategoria != 0) {
/*  75:121 */       sbSQL0.append(" AND cat.idCategoriaProducto = :idCategoria");
/*  76:    */     }
/*  77:123 */     if (idSubcategoria != 0) {
/*  78:124 */       sbSQL0.append(" AND sbcat.idSubcategoriaProducto = :idSubcategoria");
/*  79:    */     }
/*  80:126 */     if (bodega != null) {
/*  81:127 */       sbSQL0.append(" AND bo.idBodega = :idBodega");
/*  82:    */     }
/*  83:130 */     if (listaBodega != null) {
/*  84:131 */       sbSQL0.append(" AND bo IN (:listaBodega)");
/*  85:    */     }
/*  86:133 */     if (valorAtributo != "") {
/*  87:134 */       sbSQL0.append(" AND pa.valor LIKE  :valorAtributo");
/*  88:    */     }
/*  89:136 */     Query query0 = this.em.createQuery(sbSQL0.toString());
/*  90:137 */     query0.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  91:139 */     if (idCategoria != 0) {
/*  92:140 */       query0.setParameter("idCategoria", Integer.valueOf(idCategoria));
/*  93:    */     }
/*  94:142 */     if (idSubcategoria != 0) {
/*  95:143 */       query0.setParameter("idSubcategoria", Integer.valueOf(idSubcategoria));
/*  96:    */     }
/*  97:145 */     if (bodega != null) {
/*  98:146 */       query0.setParameter("idBodega", Integer.valueOf(bodega.getIdBodega()));
/*  99:    */     }
/* 100:148 */     if (valorAtributo != "") {
/* 101:149 */       query0.setParameter("valorAtributo", valorAtributo);
/* 102:    */     }
/* 103:151 */     if (listaBodega != null) {
/* 104:152 */       query0.setParameter("listaBodega", listaBodega);
/* 105:    */     }
/* 106:155 */     List<Object[]> lista0 = query0.getResultList();
/* 107:157 */     for (Object[] object : lista0)
/* 108:    */     {
/* 109:158 */       Object[] arreglo = new Object[7];
/* 110:159 */       arreglo[0] = object[2];
/* 111:160 */       arreglo[1] = object[3];
/* 112:161 */       arreglo[2] = object[4];
/* 113:162 */       arreglo[3] = object[5];
/* 114:163 */       arreglo[4] = new BigDecimal(0);
/* 115:164 */       arreglo[5] = object[6];
/* 116:165 */       arreglo[6] = object[7];
/* 117:166 */       mapa.put(((Integer)object[0]).toString() + "/" + ((Integer)object[1]).toString(), arreglo);
/* 118:    */     }
/* 119:173 */     StringBuilder sbSQL = new StringBuilder();
/* 120:174 */     sbSQL.append(" SELECT pro.idProducto,bg.idBodega,pro.codigo,pro.nombre,subc.nombre,bg.nombre, max(saldp.fecha) ");
/* 121:175 */     sbSQL.append(" FROM SaldoProducto saldp ");
/* 122:176 */     sbSQL.append(" INNER JOIN saldp.producto pro ");
/* 123:177 */     sbSQL.append(" INNER JOIN saldp.bodega bg ");
/* 124:178 */     sbSQL.append(" INNER JOIN pro.subcategoriaProducto subc ");
/* 125:179 */     if (valorAtributo != "") {
/* 126:180 */       sbSQL.append(" INNER JOIN pro.listaProductoAtributo pa ");
/* 127:    */     }
/* 128:182 */     sbSQL.append(" WHERE pro.idOrganizacion = :idOrganizacion ");
/* 129:183 */     if (idCategoria != 0) {
/* 130:184 */       sbSQL.append(" AND subc.categoriaProducto.idCategoriaProducto = :idCategoria");
/* 131:    */     }
/* 132:186 */     if (idSubcategoria != 0) {
/* 133:187 */       sbSQL.append(" AND subc.idSubcategoriaProducto = :idSubcategoria");
/* 134:    */     }
/* 135:189 */     if (bodega != null) {
/* 136:190 */       sbSQL.append(" AND bg.idBodega = :idBodega");
/* 137:    */     }
/* 138:192 */     if (valorAtributo != "") {
/* 139:193 */       sbSQL.append(" AND pa.valor LIKE  :valorAtributo");
/* 140:    */     }
/* 141:195 */     if (listaBodega != null) {
/* 142:196 */       sbSQL.append(" AND bg IN (:listaBodega)");
/* 143:    */     }
/* 144:199 */     sbSQL.append(" GROUP BY pro.codigo,pro.nombre,subc.nombre,bg.nombre,pro.idProducto,bg.idBodega");
/* 145:200 */     sbSQL.append(" ORDER BY pro.nombre");
/* 146:    */     
/* 147:202 */     Query query = this.em.createQuery(sbSQL.toString());
/* 148:203 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 149:205 */     if (idCategoria != 0) {
/* 150:206 */       query.setParameter("idCategoria", Integer.valueOf(idCategoria));
/* 151:    */     }
/* 152:208 */     if (idSubcategoria != 0) {
/* 153:209 */       query.setParameter("idSubcategoria", Integer.valueOf(idSubcategoria));
/* 154:    */     }
/* 155:211 */     if (bodega != null) {
/* 156:212 */       query.setParameter("idBodega", Integer.valueOf(bodega.getIdBodega()));
/* 157:    */     }
/* 158:214 */     if (valorAtributo != "") {
/* 159:215 */       query.setParameter("valorAtributo", valorAtributo);
/* 160:    */     }
/* 161:218 */     if (listaBodega != null) {
/* 162:219 */       query.setParameter("listaBodega", listaBodega);
/* 163:    */     }
/* 164:222 */     List<Object[]> lista = query.getResultList();
/* 165:225 */     for (Object[] objects : lista)
/* 166:    */     {
/* 167:226 */       Object[] objeto = new Object[7];
/* 168:    */       
/* 169:228 */       objeto[0] = objects[2];
/* 170:229 */       objeto[1] = objects[3];
/* 171:230 */       objeto[2] = objects[4];
/* 172:231 */       objeto[3] = objects[5];
/* 173:    */       
/* 174:233 */       Date fechaSaldo = (Date)objects[6];
/* 175:234 */       BigDecimal saldo = new BigDecimal(0);
/* 176:235 */       BigDecimal saldoMinimo = new BigDecimal(0);
/* 177:236 */       BigDecimal saldoMaximo = new BigDecimal(0);
/* 178:    */       
/* 179:238 */       StringBuilder sbSQL1 = new StringBuilder("SELECT saldp.saldo FROM SaldoProducto saldp");
/* 180:239 */       sbSQL1.append(" INNER JOIN saldp.producto pro");
/* 181:240 */       sbSQL1.append(" INNER JOIN saldp.bodega bg");
/* 182:241 */       sbSQL1.append(" WHERE saldp.fecha = :fecha");
/* 183:242 */       sbSQL1.append(" AND pro.idProducto = :idProducto");
/* 184:243 */       sbSQL1.append(" AND bg.idBodega = :idBodega");
/* 185:    */       
/* 186:245 */       Query query1 = this.em.createQuery(sbSQL1.toString());
/* 187:246 */       query1.setParameter("idProducto", (Integer)objects[0]);
/* 188:247 */       query1.setParameter("fecha", fechaSaldo);
/* 189:248 */       query1.setParameter("idBodega", (Integer)objects[1]);
/* 190:    */       
/* 191:250 */       query1.setMaxResults(1);
/* 192:251 */       List lista1 = query1.getResultList();
/* 193:252 */       if (lista1.size() > 0) {
/* 194:253 */         saldo = (BigDecimal)lista1.get(0);
/* 195:    */       }
/* 196:255 */       objeto[4] = saldo;
/* 197:    */       
/* 198:257 */       String key = ((Integer)objects[0]).toString() + "/" + ((Integer)objects[1]).toString();
/* 199:258 */       if (mapa.get(key) != null)
/* 200:    */       {
/* 201:259 */         saldoMinimo = (BigDecimal)((Object[])(Object[])mapa.get(key))[5];
/* 202:260 */         saldoMaximo = (BigDecimal)((Object[])(Object[])mapa.get(key))[6];
/* 203:    */       }
/* 204:262 */       objeto[5] = saldoMinimo;
/* 205:263 */       objeto[6] = saldoMaximo;
/* 206:    */       
/* 207:265 */       mapa.put(key, objeto);
/* 208:    */     }
/* 209:269 */     Object listaResult = new ArrayList();
/* 210:270 */     Iterator it = mapa.entrySet().iterator();
/* 211:271 */     while (it.hasNext())
/* 212:    */     {
/* 213:272 */       Map.Entry e = (Map.Entry)it.next();
/* 214:273 */       BigDecimal saldoMinimo = (BigDecimal)((Object[])(Object[])e.getValue())[5];
/* 215:274 */       BigDecimal saldo = (BigDecimal)((Object[])(Object[])e.getValue())[4];
/* 216:275 */       if ((!indicadorBajoSaldoMinimo) || (saldoMinimo.compareTo(saldo) == 1)) {
/* 217:276 */         ((List)listaResult).add((Object[])e.getValue());
/* 218:    */       }
/* 219:    */     }
/* 220:280 */     return listaResult;
/* 221:    */   }
/* 222:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ProductoBodegaDao
 * JD-Core Version:    0.7.0.1
 */