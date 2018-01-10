/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.FactorConversion;
/*   4:    */ import com.asinfo.as2.dao.UnidadConversionDao;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.Unidad;
/*   8:    */ import com.asinfo.as2.entities.UnidadConversion;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  11:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  12:    */ import java.math.BigDecimal;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.ejb.Singleton;
/*  19:    */ 
/*  20:    */ @Singleton
/*  21:    */ public class ServicioUnidadConversionImpl
/*  22:    */   implements ServicioUnidadConversion
/*  23:    */ {
/*  24:    */   @EJB
/*  25:    */   private UnidadConversionDao unidadConversionDao;
/*  26:    */   private HashMap<Integer, HashMap<String, UnidadConversion>> unidadesProducto;
/*  27:    */   private HashMap<Integer, HashMap<String, UnidadConversion>> unidadesSubcategoriaProducto;
/*  28:    */   private HashMap<String, UnidadConversion> unidades;
/*  29:    */   
/*  30:    */   public UnidadConversion getUnidadConversion(int idUnidadOrigen, int idUnidadDestino)
/*  31:    */   {
/*  32: 47 */     return this.unidadConversionDao.getUnidadConversion(idUnidadOrigen, idUnidadDestino);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<UnidadConversion> obtenerUnidadConversionConProducto()
/*  36:    */   {
/*  37: 57 */     return this.unidadConversionDao.obtenerUnidadConversionConProducto();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<UnidadConversion> obtenerUnidadConversionConProducto(Producto producto)
/*  41:    */   {
/*  42: 67 */     return this.unidadConversionDao.obtenerUnidadConversionConProducto(producto);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<UnidadConversion> obtenerUnidadConversionConSubcategoriaProducto()
/*  46:    */   {
/*  47: 72 */     return this.unidadConversionDao.obtenerUnidadConversionConSubcategoriaProducto();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List<UnidadConversion> obtenerUnidadConversionSinProductoSubcategoriaProducto()
/*  51:    */   {
/*  52: 77 */     return this.unidadConversionDao.obtenerUnidadConversionSinProductoSubcategoriaProducto();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public FactorConversion obtenerFactorConversion(int idProducto, int idSubcategoriaProducto, int idUnidadOrigen, int idUnidadDestino)
/*  56:    */     throws ExcepcionAS2Inventario
/*  57:    */   {
/*  58: 84 */     if (idUnidadOrigen == idUnidadDestino) {
/*  59: 85 */       return new FactorConversion(new BigDecimal(1), false);
/*  60:    */     }
/*  61: 88 */     String clave = idUnidadOrigen + "~" + idUnidadDestino;
/*  62: 89 */     String claveInverso = idUnidadDestino + "~" + idUnidadOrigen;
/*  63:    */     
/*  64: 91 */     Map<String, UnidadConversion> hmConversion = (Map)getUnidadesProducto().get(Integer.valueOf(idProducto));
/*  65: 92 */     if (hmConversion != null)
/*  66:    */     {
/*  67: 94 */       if (hmConversion.containsKey(clave)) {
/*  68: 95 */         return new FactorConversion(((UnidadConversion)hmConversion.get(clave)).getValorConversion(), false);
/*  69:    */       }
/*  70: 96 */       if (hmConversion.containsKey(claveInverso)) {
/*  71: 97 */         return new FactorConversion(((UnidadConversion)hmConversion.get(claveInverso)).getValorConversion(), true);
/*  72:    */       }
/*  73:    */     }
/*  74:101 */     hmConversion = (Map)getUnidadesSubcategoriaProducto().get(Integer.valueOf(idSubcategoriaProducto));
/*  75:102 */     if (hmConversion != null)
/*  76:    */     {
/*  77:104 */       if (hmConversion.containsKey(clave)) {
/*  78:105 */         return new FactorConversion(((UnidadConversion)hmConversion.get(clave)).getValorConversion(), false);
/*  79:    */       }
/*  80:106 */       if (hmConversion.containsKey(claveInverso)) {
/*  81:107 */         return new FactorConversion(((UnidadConversion)hmConversion.get(claveInverso)).getValorConversion(), true);
/*  82:    */       }
/*  83:    */     }
/*  84:112 */     if (getUnidades().containsKey(clave)) {
/*  85:113 */       return new FactorConversion(((UnidadConversion)getUnidades().get(clave)).getValorConversion(), false);
/*  86:    */     }
/*  87:114 */     if (getUnidades().containsKey(claveInverso)) {
/*  88:115 */       return new FactorConversion(((UnidadConversion)getUnidades().get(claveInverso)).getValorConversion(), true);
/*  89:    */     }
/*  90:118 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public List<Unidad> obtenerListaUnidadConversionPorProducto(int idProducto, int idSubcategoriaProducto, int idUnidadOrigen)
/*  94:    */     throws ExcepcionAS2
/*  95:    */   {
/*  96:125 */     HashMap<Integer, Unidad> mapa = new HashMap();
/*  97:    */     
/*  98:127 */     HashMap<String, UnidadConversion> hmConversiones = (HashMap)getUnidadesProducto().get(Integer.valueOf(idProducto));
/*  99:128 */     if (hmConversiones != null) {
/* 100:129 */       for (UnidadConversion unidadConversion : hmConversiones.values())
/* 101:    */       {
/* 102:130 */         if ((unidadConversion.getUnidadOrigen().getIdUnidad() == idUnidadOrigen) && 
/* 103:131 */           (!mapa.containsKey(Integer.valueOf(unidadConversion.getUnidadDestino().getIdUnidad())))) {
/* 104:132 */           mapa.put(Integer.valueOf(unidadConversion.getUnidadDestino().getId()), unidadConversion.getUnidadDestino());
/* 105:    */         }
/* 106:135 */         if ((unidadConversion.getUnidadDestino().getIdUnidad() == idUnidadOrigen) && 
/* 107:136 */           (!mapa.containsKey(Integer.valueOf(unidadConversion.getUnidadDestino().getIdUnidad())))) {
/* 108:137 */           mapa.put(Integer.valueOf(unidadConversion.getUnidadOrigen().getId()), unidadConversion.getUnidadOrigen());
/* 109:    */         }
/* 110:    */       }
/* 111:    */     }
/* 112:143 */     hmConversiones = (HashMap)getUnidadesSubcategoriaProducto().get(Integer.valueOf(idSubcategoriaProducto));
/* 113:144 */     if (hmConversiones != null) {
/* 114:145 */       for (UnidadConversion unidadConversion : hmConversiones.values())
/* 115:    */       {
/* 116:146 */         if ((unidadConversion.getUnidadOrigen().getIdUnidad() == idUnidadOrigen) && 
/* 117:147 */           (!mapa.containsKey(Integer.valueOf(unidadConversion.getUnidadDestino().getIdUnidad())))) {
/* 118:148 */           mapa.put(Integer.valueOf(unidadConversion.getUnidadDestino().getId()), unidadConversion.getUnidadDestino());
/* 119:    */         }
/* 120:151 */         if ((unidadConversion.getUnidadDestino().getIdUnidad() == idUnidadOrigen) && 
/* 121:152 */           (!mapa.containsKey(Integer.valueOf(unidadConversion.getUnidadDestino().getIdUnidad())))) {
/* 122:153 */           mapa.put(Integer.valueOf(unidadConversion.getUnidadOrigen().getId()), unidadConversion.getUnidadOrigen());
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:159 */     for (UnidadConversion unidadConversion : getUnidades().values())
/* 127:    */     {
/* 128:160 */       if (!mapa.containsKey(Integer.valueOf(unidadConversion.getUnidadDestino().getId()))) {
/* 129:161 */         mapa.put(Integer.valueOf(unidadConversion.getUnidadDestino().getId()), unidadConversion.getUnidadDestino());
/* 130:    */       }
/* 131:163 */       if (!mapa.containsKey(Integer.valueOf(unidadConversion.getUnidadDestino().getIdUnidad()))) {
/* 132:164 */         mapa.put(Integer.valueOf(unidadConversion.getUnidadDestino().getId()), unidadConversion.getUnidadOrigen());
/* 133:    */       }
/* 134:    */     }
/* 135:168 */     return new ArrayList(mapa.values());
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void actualizaUnidadConversionProducto(Producto producto)
/* 139:    */   {
/* 140:173 */     if (getUnidadesProducto().containsKey(Integer.valueOf(producto.getIdProducto()))) {
/* 141:174 */       getUnidadesProducto().remove(Integer.valueOf(producto.getIdProducto()));
/* 142:    */     }
/* 143:176 */     for (UnidadConversion u : obtenerUnidadConversionConProducto(producto))
/* 144:    */     {
/* 145:178 */       if (!getUnidadesProducto().containsKey(Integer.valueOf(u.getProducto().getIdProducto()))) {
/* 146:179 */         getUnidadesProducto().put(Integer.valueOf(u.getProducto().getIdProducto()), new HashMap());
/* 147:    */       }
/* 148:181 */       String clave = u.getUnidadOrigen().getIdUnidad() + "~" + u.getUnidadDestino().getIdUnidad();
/* 149:182 */       ((HashMap)getUnidadesProducto().get(Integer.valueOf(u.getProducto().getIdProducto()))).put(clave, u);
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void actualizaUnidadConversion(UnidadConversion unidadConversion)
/* 154:    */   {
/* 155:189 */     String clave = unidadConversion.getUnidadOrigen().getIdUnidad() + "~" + unidadConversion.getUnidadDestino().getIdUnidad();
/* 156:190 */     if (unidadConversion.getProducto() != null)
/* 157:    */     {
/* 158:192 */       if (getUnidadesProducto().containsKey(Integer.valueOf(unidadConversion.getProducto().getIdProducto())))
/* 159:    */       {
/* 160:193 */         if (((HashMap)getUnidadesProducto().get(Integer.valueOf(unidadConversion.getProducto().getIdProducto()))).containsKey(clave)) {
/* 161:195 */           ((UnidadConversion)((HashMap)getUnidadesProducto().get(Integer.valueOf(unidadConversion.getProducto().getIdProducto()))).get(clave)).setValorConversion(unidadConversion.getValorConversion());
/* 162:    */         }
/* 163:    */       }
/* 164:    */       else
/* 165:    */       {
/* 166:199 */         getUnidadesProducto().put(Integer.valueOf(unidadConversion.getProducto().getIdProducto()), new HashMap());
/* 167:200 */         ((HashMap)getUnidadesProducto().get(Integer.valueOf(unidadConversion.getProducto().getIdProducto()))).put(clave, unidadConversion);
/* 168:    */       }
/* 169:    */     }
/* 170:202 */     else if (unidadConversion.getSubcategoriaProducto() != null)
/* 171:    */     {
/* 172:203 */       if (getUnidadesSubcategoriaProducto().containsKey(Integer.valueOf(unidadConversion.getSubcategoriaProducto().getIdSubcategoriaProducto())))
/* 173:    */       {
/* 174:204 */         if (((HashMap)getUnidadesSubcategoriaProducto().get(Integer.valueOf(unidadConversion.getSubcategoriaProducto().getIdSubcategoriaProducto()))).containsKey(clave)) {
/* 175:206 */           ((UnidadConversion)((HashMap)getUnidadesSubcategoriaProducto().get(Integer.valueOf(unidadConversion.getSubcategoriaProducto().getIdSubcategoriaProducto()))).get(clave)).setValorConversion(unidadConversion.getValorConversion());
/* 176:    */         }
/* 177:    */       }
/* 178:    */       else
/* 179:    */       {
/* 180:209 */         getUnidadesSubcategoriaProducto().put(Integer.valueOf(unidadConversion.getSubcategoriaProducto().getIdSubcategoriaProducto()), new HashMap());
/* 181:210 */         ((HashMap)getUnidadesSubcategoriaProducto().get(Integer.valueOf(unidadConversion.getSubcategoriaProducto().getIdSubcategoriaProducto()))).put(clave, unidadConversion);
/* 182:    */       }
/* 183:    */     }
/* 184:213 */     else if (getUnidades().containsKey(clave)) {
/* 185:214 */       ((UnidadConversion)getUnidades().get(clave)).setValorConversion(unidadConversion.getValorConversion());
/* 186:    */     } else {
/* 187:217 */       getUnidades().put(clave, unidadConversion);
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public HashMap<Integer, HashMap<String, UnidadConversion>> getUnidadesProducto()
/* 192:    */   {
/* 193:228 */     if (this.unidadesProducto == null)
/* 194:    */     {
/* 195:229 */       this.unidadesProducto = new HashMap();
/* 196:231 */       for (UnidadConversion u : obtenerUnidadConversionConProducto())
/* 197:    */       {
/* 198:233 */         if (!this.unidadesProducto.containsKey(Integer.valueOf(u.getProducto().getIdProducto()))) {
/* 199:234 */           this.unidadesProducto.put(Integer.valueOf(u.getProducto().getIdProducto()), new HashMap());
/* 200:    */         }
/* 201:236 */         String clave = u.getUnidadOrigen().getIdUnidad() + "~" + u.getUnidadDestino().getIdUnidad();
/* 202:237 */         ((HashMap)this.unidadesProducto.get(Integer.valueOf(u.getProducto().getIdProducto()))).put(clave, u);
/* 203:    */       }
/* 204:    */     }
/* 205:241 */     return this.unidadesProducto;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public HashMap<Integer, HashMap<String, UnidadConversion>> getUnidadesSubcategoriaProducto()
/* 209:    */   {
/* 210:250 */     if (this.unidadesSubcategoriaProducto == null)
/* 211:    */     {
/* 212:251 */       this.unidadesSubcategoriaProducto = new HashMap();
/* 213:253 */       for (UnidadConversion u : obtenerUnidadConversionConSubcategoriaProducto())
/* 214:    */       {
/* 215:254 */         if (!this.unidadesSubcategoriaProducto.containsKey(Integer.valueOf(u.getSubcategoriaProducto().getIdSubcategoriaProducto()))) {
/* 216:256 */           this.unidadesSubcategoriaProducto.put(Integer.valueOf(u.getSubcategoriaProducto().getIdSubcategoriaProducto()), new HashMap());
/* 217:    */         }
/* 218:258 */         String clave = u.getUnidadOrigen().getIdUnidad() + "~" + u.getUnidadDestino().getIdUnidad();
/* 219:259 */         ((HashMap)this.unidadesSubcategoriaProducto.get(Integer.valueOf(u.getSubcategoriaProducto().getIdSubcategoriaProducto()))).put(clave, u);
/* 220:    */       }
/* 221:    */     }
/* 222:264 */     return this.unidadesSubcategoriaProducto;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public HashMap<String, UnidadConversion> getUnidades()
/* 226:    */   {
/* 227:273 */     if (this.unidades == null)
/* 228:    */     {
/* 229:274 */       this.unidades = new HashMap();
/* 230:275 */       for (UnidadConversion u : obtenerUnidadConversionSinProductoSubcategoriaProducto())
/* 231:    */       {
/* 232:276 */         String clave = u.getUnidadOrigen().getIdUnidad() + "~" + u.getUnidadDestino().getIdUnidad();
/* 233:277 */         this.unidades.put(clave, u);
/* 234:    */       }
/* 235:    */     }
/* 236:281 */     return this.unidades;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setUnidades(HashMap<String, UnidadConversion> unidades)
/* 240:    */   {
/* 241:291 */     this.unidades = unidades;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void cargarListaUnidadConversion(Producto producto)
/* 245:    */     throws ExcepcionAS2
/* 246:    */   {
/* 247:302 */     producto.getTraListaUnidadConversion().clear();
/* 248:303 */     producto.getTraListaUnidadConversion().addAll(
/* 249:304 */       obtenerListaUnidadConversionPorProducto(producto.getIdProducto(), producto.getSubcategoriaProducto().getIdSubcategoriaProducto(), producto
/* 250:305 */       .getUnidad().getIdUnidad()));
/* 251:306 */     if ((producto.getUnidadVenta() != null) && (!producto.getTraListaUnidadConversion().contains(producto.getUnidadVenta()))) {
/* 252:307 */       producto.getTraListaUnidadConversion().add(producto.getUnidadVenta());
/* 253:    */     }
/* 254:309 */     if ((producto.getUnidadCompra() != null) && (!producto.getTraListaUnidadConversion().contains(producto.getUnidadCompra()))) {
/* 255:310 */       producto.getTraListaUnidadConversion().add(producto.getUnidadCompra());
/* 256:    */     }
/* 257:312 */     if ((producto.getUnidadAlmacenamiento() != null) && (!producto.getTraListaUnidadConversion().contains(producto.getUnidadAlmacenamiento()))) {
/* 258:313 */       producto.getTraListaUnidadConversion().add(producto.getUnidadAlmacenamiento());
/* 259:    */     }
/* 260:315 */     if ((producto.getUnidad() != null) && (!producto.getTraListaUnidadConversion().contains(producto.getUnidad()))) {
/* 261:316 */       producto.getTraListaUnidadConversion().add(producto.getUnidad());
/* 262:    */     }
/* 263:    */   }
/* 264:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioUnidadConversionImpl
 * JD-Core Version:    0.7.0.1
 */