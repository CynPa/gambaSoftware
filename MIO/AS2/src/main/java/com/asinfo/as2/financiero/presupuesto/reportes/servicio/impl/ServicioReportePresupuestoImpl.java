/*   1:    */ package com.asinfo.as2.financiero.presupuesto.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.reportes.financiero.presupuesto.ReportePresupuestoDao;
/*   4:    */ import com.asinfo.as2.entities.CentroCosto;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Ejercicio;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*  11:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  13:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  15:    */ import com.asinfo.as2.financiero.presupuesto.reportes.servicio.ServicioReportePresupuesto;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.Iterator;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.ejb.Stateless;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ public class ServicioReportePresupuestoImpl
/*  28:    */   implements ServicioReportePresupuesto
/*  29:    */ {
/*  30:    */   @EJB
/*  31:    */   private ReportePresupuestoDao reportePresupuestoDao;
/*  32:    */   @EJB
/*  33:    */   private ServicioDimensionContable servicioDimensionContable;
/*  34:    */   
/*  35:    */   public List<Object[]> getReportePresupuesto(Ejercicio ejercicio, Mes mes, Sucursal sucursal, boolean indicadorNIIF, boolean indicadorAcumulado, List<CuentaContable> listaCuentaContable, int idOrganizacion, DimensionContable dimensionContable, String dimensionContablePresupuesto)
/*  36:    */   {
/*  37: 55 */     return this.reportePresupuestoDao.getReportePresupuesto(ejercicio, mes, sucursal, indicadorAcumulado, listaCuentaContable, idOrganizacion, dimensionContable, dimensionContablePresupuesto);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<DetalleAsiento> getMayorPartidaPresupuestaria(PartidaPresupuestaria partidaPresupuestaria, Date fechaDesde, Date fechaHasta, int idSucursal, boolean indicadorNIIF, CentroCosto centroCosto)
/*  41:    */   {
/*  42: 68 */     return this.reportePresupuestoDao.getMayorPartidaPresupuestaria(partidaPresupuestaria, fechaDesde, fechaHasta, idSucursal, indicadorNIIF, centroCosto);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public BigDecimal obtenerSaldo(Date fechaDesde, Date fechaHasta, ValoresCalculo valoresCalculo, TipoCalculo tipoCalculo, boolean indicadorNIIF, int idSucursal, PartidaPresupuestaria partidaPresupuestaria, CentroCosto centroCosto)
/*  46:    */   {
/*  47: 82 */     return this.reportePresupuestoDao.obtenerSaldo(fechaDesde, fechaHasta, valoresCalculo, tipoCalculo, indicadorNIIF, idSucursal, partidaPresupuestaria, centroCosto);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List getReporteMayorPartidaPresupuestaria(PartidaPresupuestaria partidaPresupuestaria, Date fechaDesde, Date fechaHasta, int idSucursal, boolean indicadorNIIF, CentroCosto centroCosto)
/*  51:    */   {
/*  52: 96 */     return this.reportePresupuestoDao.getReporteMayorPartidaPresupuestaria(partidaPresupuestaria, fechaDesde, fechaHasta, idSucursal, indicadorNIIF, centroCosto);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<Object[]> getReportePresupuesto(Ejercicio ejercicio, Mes mes, int longitudCodigo, DimensionContable dimensionContable, List<DimensionContable> listDimensionContable, int tipoReporte, int idUsuario, int idOrganizacion)
/*  56:    */   {
/*  57:103 */     List<Object[]> listResult = new ArrayList();
/*  58:104 */     List<DimensionContable> listDimensionesHijas = new ArrayList();
/*  59:105 */     Map<String, String> filters = new HashMap();
/*  60:106 */     filters.put("idOrganizacion", "" + idOrganizacion);
/*  61:107 */     filters.put("activo", "true");
/*  62:108 */     if ((longitudCodigo != 0) && (dimensionContable != null))
/*  63:    */     {
/*  64:109 */       filters.put("codigo", dimensionContable.getCodigo() + "%");
/*  65:110 */       filters.put("numero", dimensionContable.getNumero());
/*  66:111 */       listDimensionesHijas = this.servicioDimensionContable.obtenerListaCombo("codigo", true, filters);
/*  67:    */     }
/*  68:112 */     else if ((longitudCodigo != 0) && (dimensionContable == null))
/*  69:    */     {
/*  70:113 */       for (DimensionContable dc : listDimensionContable)
/*  71:    */       {
/*  72:114 */         filters.put("codigo", dc.getCodigo() + "%");
/*  73:115 */         filters.put("numero", dc.getNumero());
/*  74:116 */         listDimensionesHijas.addAll(this.servicioDimensionContable.obtenerListaCombo("codigo", true, filters));
/*  75:    */       }
/*  76:    */     }
/*  77:    */     BigDecimal valorEnero;
/*  78:    */     BigDecimal valorFebrero;
/*  79:    */     BigDecimal valorMarzo;
/*  80:    */     BigDecimal valorAbril;
/*  81:    */     BigDecimal valorMayo;
/*  82:    */     BigDecimal valorJunio;
/*  83:    */     BigDecimal valorJulio;
/*  84:    */     BigDecimal valorAgosto;
/*  85:    */     BigDecimal valorSeptiembre;
/*  86:    */     BigDecimal valorOctubre;
/*  87:    */     BigDecimal valorNoviembre;
/*  88:    */     BigDecimal valorDiciembre;
/*  89:    */     Object object;
/*  90:    */     Object object;
/*  91:120 */     if ((listDimensionesHijas.size() > 1) && (tipoReporte == 0))
/*  92:    */     {
/*  93:121 */       valorEnero = BigDecimal.ZERO;
/*  94:122 */       valorFebrero = BigDecimal.ZERO;
/*  95:123 */       valorMarzo = BigDecimal.ZERO;
/*  96:124 */       valorAbril = BigDecimal.ZERO;
/*  97:125 */       valorMayo = BigDecimal.ZERO;
/*  98:126 */       valorJunio = BigDecimal.ZERO;
/*  99:127 */       valorJulio = BigDecimal.ZERO;
/* 100:128 */       valorAgosto = BigDecimal.ZERO;
/* 101:129 */       valorSeptiembre = BigDecimal.ZERO;
/* 102:130 */       valorOctubre = BigDecimal.ZERO;
/* 103:131 */       valorNoviembre = BigDecimal.ZERO;
/* 104:132 */       valorDiciembre = BigDecimal.ZERO;
/* 105:134 */       if (dimensionContable != null)
/* 106:    */       {
/* 107:137 */         for (Object[] arrayObject : this.reportePresupuestoDao.getReportePresupuesto(ejercicio, mes, longitudCodigo, dimensionContable, listDimensionContable, 0, idUsuario, idOrganizacion))
/* 108:    */         {
/* 109:139 */           valorEnero = valorEnero.add((BigDecimal)arrayObject[5]);
/* 110:140 */           valorFebrero = valorFebrero.add((BigDecimal)arrayObject[6]);
/* 111:141 */           valorMarzo = valorMarzo.add((BigDecimal)arrayObject[7]);
/* 112:142 */           valorAbril = valorAbril.add((BigDecimal)arrayObject[8]);
/* 113:143 */           valorMayo = valorMayo.add((BigDecimal)arrayObject[9]);
/* 114:144 */           valorJunio = valorJunio.add((BigDecimal)arrayObject[10]);
/* 115:145 */           valorJulio = valorJulio.add((BigDecimal)arrayObject[11]);
/* 116:146 */           valorAgosto = valorAgosto.add((BigDecimal)arrayObject[12]);
/* 117:147 */           valorSeptiembre = valorSeptiembre.add((BigDecimal)arrayObject[13]);
/* 118:148 */           valorOctubre = valorOctubre.add((BigDecimal)arrayObject[14]);
/* 119:149 */           valorNoviembre = valorNoviembre.add((BigDecimal)arrayObject[15]);
/* 120:150 */           valorDiciembre = valorDiciembre.add((BigDecimal)arrayObject[16]);
/* 121:    */         }
/* 122:152 */         object = new Object[] { ejercicio.getNombre(), dimensionContable.getCodigo(), dimensionContable.getNombre(), null, null, valorEnero, valorFebrero, valorMarzo, valorAbril, valorMayo, valorJunio, valorJulio, valorAgosto, valorSeptiembre, valorOctubre, valorNoviembre, valorDiciembre };
/* 123:    */         
/* 124:    */ 
/* 125:155 */         listResult.add(object);
/* 126:    */       }
/* 127:158 */       else if (listDimensionesHijas.size() > listDimensionContable.size())
/* 128:    */       {
/* 129:160 */         for (object = listDimensionContable.iterator(); ((Iterator)object).hasNext();)
/* 130:    */         {
/* 131:160 */           DimensionContable dc = (DimensionContable)((Iterator)object).next();
/* 132:161 */           for (Object[] arrayObject : this.reportePresupuestoDao.getReportePresupuesto(ejercicio, mes, longitudCodigo, dc, listDimensionContable, 0, idUsuario, idOrganizacion))
/* 133:    */           {
/* 134:163 */             valorEnero = valorEnero.add((BigDecimal)arrayObject[5]);
/* 135:164 */             valorFebrero = valorFebrero.add((BigDecimal)arrayObject[6]);
/* 136:165 */             valorMarzo = valorMarzo.add((BigDecimal)arrayObject[7]);
/* 137:166 */             valorAbril = valorAbril.add((BigDecimal)arrayObject[8]);
/* 138:167 */             valorMayo = valorMayo.add((BigDecimal)arrayObject[9]);
/* 139:168 */             valorJunio = valorJunio.add((BigDecimal)arrayObject[10]);
/* 140:169 */             valorJulio = valorJulio.add((BigDecimal)arrayObject[11]);
/* 141:170 */             valorAgosto = valorAgosto.add((BigDecimal)arrayObject[12]);
/* 142:171 */             valorSeptiembre = valorSeptiembre.add((BigDecimal)arrayObject[13]);
/* 143:172 */             valorOctubre = valorOctubre.add((BigDecimal)arrayObject[14]);
/* 144:173 */             valorNoviembre = valorNoviembre.add((BigDecimal)arrayObject[15]);
/* 145:174 */             valorDiciembre = valorDiciembre.add((BigDecimal)arrayObject[16]);
/* 146:    */           }
/* 147:176 */           object = new Object[] { ejercicio.getNombre(), dc.getCodigo(), dc.getNombre(), null, null, valorEnero, valorFebrero, valorMarzo, valorAbril, valorMayo, valorJunio, valorJulio, valorAgosto, valorSeptiembre, valorOctubre, valorNoviembre, valorDiciembre };
/* 148:    */           
/* 149:    */ 
/* 150:179 */           listResult.add(object);
/* 151:180 */           valorEnero = BigDecimal.ZERO;
/* 152:181 */           valorFebrero = BigDecimal.ZERO;
/* 153:182 */           valorMarzo = BigDecimal.ZERO;
/* 154:183 */           valorAbril = BigDecimal.ZERO;
/* 155:184 */           valorMayo = BigDecimal.ZERO;
/* 156:185 */           valorJunio = BigDecimal.ZERO;
/* 157:186 */           valorJulio = BigDecimal.ZERO;
/* 158:187 */           valorAgosto = BigDecimal.ZERO;
/* 159:188 */           valorSeptiembre = BigDecimal.ZERO;
/* 160:189 */           valorOctubre = BigDecimal.ZERO;
/* 161:190 */           valorNoviembre = BigDecimal.ZERO;
/* 162:191 */           valorDiciembre = BigDecimal.ZERO;
/* 163:    */         }
/* 164:    */       }
/* 165:    */       else
/* 166:    */       {
/* 167:196 */         for (object = listDimensionesHijas.iterator(); ((Iterator)object).hasNext();)
/* 168:    */         {
/* 169:196 */           DimensionContable dc = (DimensionContable)((Iterator)object).next();
/* 170:197 */           for (object = this.reportePresupuestoDao.getReportePresupuesto(ejercicio, mes, longitudCodigo, dc, listDimensionContable, 0, idUsuario, idOrganizacion).iterator(); ((Iterator)object).hasNext();)
/* 171:    */           {
/* 172:197 */             Object[] arrayObject = (Object[])((Iterator)object).next();
/* 173:    */             
/* 174:199 */             valorEnero = valorEnero.add((BigDecimal)arrayObject[5]);
/* 175:200 */             valorFebrero = valorFebrero.add((BigDecimal)arrayObject[6]);
/* 176:201 */             valorMarzo = valorMarzo.add((BigDecimal)arrayObject[7]);
/* 177:202 */             valorAbril = valorAbril.add((BigDecimal)arrayObject[8]);
/* 178:203 */             valorMayo = valorMayo.add((BigDecimal)arrayObject[9]);
/* 179:204 */             valorJunio = valorJunio.add((BigDecimal)arrayObject[10]);
/* 180:205 */             valorJulio = valorJulio.add((BigDecimal)arrayObject[11]);
/* 181:206 */             valorAgosto = valorAgosto.add((BigDecimal)arrayObject[12]);
/* 182:207 */             valorSeptiembre = valorSeptiembre.add((BigDecimal)arrayObject[13]);
/* 183:208 */             valorOctubre = valorOctubre.add((BigDecimal)arrayObject[14]);
/* 184:209 */             valorNoviembre = valorNoviembre.add((BigDecimal)arrayObject[15]);
/* 185:210 */             valorDiciembre = valorDiciembre.add((BigDecimal)arrayObject[16]);
/* 186:    */           }
/* 187:212 */           Object[] object = { ejercicio.getNombre(), dc.getCodigo(), dc.getNombre(), null, null, valorEnero, valorFebrero, valorMarzo, valorAbril, valorMayo, valorJunio, valorJulio, valorAgosto, valorSeptiembre, valorOctubre, valorNoviembre, valorDiciembre };
/* 188:    */           
/* 189:    */ 
/* 190:215 */           listResult.add(object);
/* 191:216 */           valorEnero = BigDecimal.ZERO;
/* 192:217 */           valorFebrero = BigDecimal.ZERO;
/* 193:218 */           valorMarzo = BigDecimal.ZERO;
/* 194:219 */           valorAbril = BigDecimal.ZERO;
/* 195:220 */           valorMayo = BigDecimal.ZERO;
/* 196:221 */           valorJunio = BigDecimal.ZERO;
/* 197:222 */           valorJulio = BigDecimal.ZERO;
/* 198:223 */           valorAgosto = BigDecimal.ZERO;
/* 199:224 */           valorSeptiembre = BigDecimal.ZERO;
/* 200:225 */           valorOctubre = BigDecimal.ZERO;
/* 201:226 */           valorNoviembre = BigDecimal.ZERO;
/* 202:227 */           valorDiciembre = BigDecimal.ZERO;
/* 203:    */         }
/* 204:    */       }
/* 205:    */     }
/* 206:    */     else
/* 207:    */     {
/* 208:234 */       listResult = this.reportePresupuestoDao.getReportePresupuesto(ejercicio, mes, longitudCodigo, dimensionContable, listDimensionContable, tipoReporte, idUsuario, idOrganizacion);
/* 209:    */     }
/* 210:237 */     return listResult;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<Object[]> getReportePresupuestoComparativo(Ejercicio ejercicio, Mes mesSeleccionado, Sucursal sucursal, int idOrganizacion, String dimensionPresupuesto, DimensionContable dimensionContable, List<DimensionContable> listaDimensionContable, int tipoReporte, int idUsuario)
/* 214:    */   {
/* 215:243 */     return this.reportePresupuestoDao.getReportePresupuestoComparativo(ejercicio, mesSeleccionado, sucursal, idOrganizacion, dimensionPresupuesto, dimensionContable, listaDimensionContable, tipoReporte, idUsuario);
/* 216:    */   }
/* 217:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.presupuesto.reportes.servicio.impl.ServicioReportePresupuestoImpl
 * JD-Core Version:    0.7.0.1
 */