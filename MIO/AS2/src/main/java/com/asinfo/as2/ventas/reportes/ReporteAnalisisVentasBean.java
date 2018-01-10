/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   6:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*   7:    */ import com.asinfo.as2.util.AppUtil;
/*   8:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  10:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  11:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Collections;
/*  15:    */ import java.util.Comparator;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.faces.model.SelectItem;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import net.sf.jasperreports.engine.JRException;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class ReporteAnalisisVentasBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 8744647487664669253L;
/*  33:    */   @EJB
/*  34:    */   private ServicioReporteVenta servicioReporteVenta;
/*  35:    */   @EJB
/*  36:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  37:    */   private Date fechaDesde;
/*  38:    */   private Date fechaHasta;
/*  39:    */   private SubcategoriaProducto subcategoriaProducto;
/*  40: 61 */   private enumTipoReporte tipoReporte = enumTipoReporte.PRODUCTO;
/*  41: 62 */   private enumTipoValor tipoValor = enumTipoValor.CANTIDAD;
/*  42:    */   private boolean anuladas;
/*  43:    */   private boolean saldoInicial;
/*  44:    */   private List<SelectItem> listaTipoReporte;
/*  45:    */   private List<SelectItem> listaTipoValor;
/*  46:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  47:    */   
/*  48:    */   private static enum enumTipoReporte
/*  49:    */   {
/*  50: 68 */     PRODUCTO,  CLIENTE;
/*  51:    */     
/*  52:    */     private enumTipoReporte() {}
/*  53:    */   }
/*  54:    */   
/*  55:    */   private static enum enumTipoValor
/*  56:    */   {
/*  57: 73 */     CANTIDAD,  PRECIO;
/*  58:    */     
/*  59:    */     private enumTipoValor() {}
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected JRDataSource getJRDataSource()
/*  63:    */   {
/*  64: 89 */     List listaDatosReporte = new ArrayList();
/*  65: 90 */     JRDataSource ds = null;
/*  66:    */     String[] fields;
/*  67:    */     String[] fields;
/*  68: 92 */     if (this.tipoReporte.equals(enumTipoReporte.PRODUCTO))
/*  69:    */     {
/*  70: 93 */       listaDatosReporte = this.servicioReporteVenta.getReporteAnalisisVentasProducto(this.fechaDesde, this.fechaHasta, this.saldoInicial, AppUtil.getOrganizacion()
/*  71: 94 */         .getId(), getSubcategoriaProducto().getId(), isIndicadorCantidad());
/*  72: 95 */       fields = new String[] { "f_nombreEmpresa", "f_codigoProducto", "f_codigoComercial", "f_nombreProducto", "f_unidad", "f_cantidad", "f_anio", "f_mes" };
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76: 97 */       listaDatosReporte = this.servicioReporteVenta.getReporteAnalisisVentasCliente(this.fechaDesde, this.fechaHasta, this.saldoInicial, AppUtil.getOrganizacion()
/*  77: 98 */         .getId(), getSubcategoriaProducto().getId(), isIndicadorCantidad());
/*  78: 99 */       Collections.sort(listaDatosReporte, new Comparator()
/*  79:    */       {
/*  80:    */         public int compare(Object[] o1, Object[] o2)
/*  81:    */         {
/*  82:102 */           return ((String)o1[1]).compareTo((String)o2[1]);
/*  83:    */         }
/*  84:105 */       });
/*  85:106 */       fields = new String[] { "f_nombreEmpresa", "f_identificacionCliente", "f_codigoProducto", "f_codigoComercial", "f_nombreProducto", "f_unidad", "f_cantidad" };
/*  86:    */     }
/*  87:110 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  88:    */     
/*  89:112 */     return ds;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected String getCompileFileName()
/*  93:    */   {
/*  94:122 */     if (this.tipoReporte.equals(enumTipoReporte.PRODUCTO)) {
/*  95:123 */       return "reporteAnalisisProducto";
/*  96:    */     }
/*  97:125 */     return "reporteAnalisisEmpresa";
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected Map<String, Object> getReportParameters()
/* 101:    */   {
/* 102:136 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 103:137 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 104:138 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 105:140 */     if (this.tipoReporte.equals(enumTipoReporte.PRODUCTO))
/* 106:    */     {
/* 107:141 */       reportParameters.put("ReportTitle", "Analisis Producto");
/* 108:142 */       reportParameters.put("p_tipoValor", this.tipoValor.toString());
/* 109:    */     }
/* 110:    */     else
/* 111:    */     {
/* 112:144 */       reportParameters.put("ReportTitle", "Analisis Empresa");
/* 113:145 */       reportParameters.put("p_tipoValor", this.tipoValor.toString());
/* 114:    */     }
/* 115:148 */     return reportParameters;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String execute()
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:158 */       validaDatos();
/* 123:159 */       super.prepareReport();
/* 124:    */     }
/* 125:    */     catch (JRException e)
/* 126:    */     {
/* 127:161 */       LOG.info("Error JRException");
/* 128:162 */       e.printStackTrace();
/* 129:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 130:    */     }
/* 131:    */     catch (IOException e)
/* 132:    */     {
/* 133:165 */       LOG.info("Error IOException");
/* 134:166 */       e.printStackTrace();
/* 135:167 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 136:    */     }
/* 137:170 */     return null;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void validaDatos()
/* 141:    */   {
/* 142:175 */     if (this.fechaDesde == null) {
/* 143:176 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 144:    */     }
/* 145:178 */     if (this.fechaHasta == null) {
/* 146:179 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Date getFechaDesde()
/* 151:    */   {
/* 152:189 */     return this.fechaDesde;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setFechaDesde(Date fechaDesde)
/* 156:    */   {
/* 157:199 */     this.fechaDesde = fechaDesde;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Date getFechaHasta()
/* 161:    */   {
/* 162:208 */     return this.fechaHasta;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setFechaHasta(Date fechaHasta)
/* 166:    */   {
/* 167:218 */     this.fechaHasta = fechaHasta;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean isAnuladas()
/* 171:    */   {
/* 172:227 */     return this.anuladas;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setAnuladas(boolean anuladas)
/* 176:    */   {
/* 177:237 */     this.anuladas = anuladas;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isSaldoInicial()
/* 181:    */   {
/* 182:246 */     return this.saldoInicial;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 186:    */   {
/* 187:255 */     if (this.subcategoriaProducto == null) {
/* 188:256 */       this.subcategoriaProducto = new SubcategoriaProducto();
/* 189:    */     }
/* 190:258 */     return this.subcategoriaProducto;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 194:    */   {
/* 195:268 */     this.subcategoriaProducto = subcategoriaProducto;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setSaldoInicial(boolean saldoInicial)
/* 199:    */   {
/* 200:278 */     this.saldoInicial = saldoInicial;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public enumTipoReporte getTipoReporte()
/* 204:    */   {
/* 205:287 */     return this.tipoReporte;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setTipoReporte(enumTipoReporte tipoReporte)
/* 209:    */   {
/* 210:297 */     this.tipoReporte = tipoReporte;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public enumTipoValor getTipoValor()
/* 214:    */   {
/* 215:306 */     return this.tipoValor;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setTipoValor(enumTipoValor tipoValor)
/* 219:    */   {
/* 220:316 */     this.tipoValor = tipoValor;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<SelectItem> getListaTipoReporte()
/* 224:    */   {
/* 225:325 */     if (this.listaTipoReporte == null)
/* 226:    */     {
/* 227:326 */       this.listaTipoReporte = new ArrayList();
/* 228:327 */       for (enumTipoReporte tr : enumTipoReporte.values()) {
/* 229:328 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 230:    */       }
/* 231:    */     }
/* 232:331 */     return this.listaTipoReporte;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 236:    */   {
/* 237:341 */     this.listaTipoReporte = listaTipoReporte;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List<SelectItem> getListaTipoValor()
/* 241:    */   {
/* 242:350 */     if (this.listaTipoValor == null)
/* 243:    */     {
/* 244:351 */       this.listaTipoValor = new ArrayList();
/* 245:352 */       for (enumTipoValor tv : enumTipoValor.values()) {
/* 246:353 */         this.listaTipoValor.add(new SelectItem(tv, tv.name()));
/* 247:    */       }
/* 248:    */     }
/* 249:356 */     return this.listaTipoValor;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setListaTipoValor(List<SelectItem> listaTipoValor)
/* 253:    */   {
/* 254:366 */     this.listaTipoValor = listaTipoValor;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 258:    */   {
/* 259:375 */     if (this.listaSubcategoriaProducto == null) {
/* 260:376 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 261:    */     }
/* 262:378 */     return this.listaSubcategoriaProducto;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 266:    */   {
/* 267:388 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public boolean isMostrarFechas()
/* 271:    */   {
/* 272:392 */     return this.tipoReporte != enumTipoReporte.PRODUCTO;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public boolean isIndicadorCantidad()
/* 276:    */   {
/* 277:396 */     return getTipoValor() == enumTipoValor.CANTIDAD;
/* 278:    */   }
/* 279:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteAnalisisVentasBean
 * JD-Core Version:    0.7.0.1
 */