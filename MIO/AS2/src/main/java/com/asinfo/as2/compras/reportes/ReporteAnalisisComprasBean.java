/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   7:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  10:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import javax.faces.model.SelectItem;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import net.sf.jasperreports.engine.JRException;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ReporteAnalisisComprasBean
/*  27:    */   extends AbstractBaseReportBean
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 8744647487664669253L;
/*  30:    */   @EJB
/*  31:    */   private ServicioReporteCompra servicioReporteCompra;
/*  32:    */   @EJB
/*  33:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  34:    */   private Date fechaDesde;
/*  35:    */   private Date fechaHasta;
/*  36:    */   private SubcategoriaProducto subcategoriaProducto;
/*  37: 58 */   private enumTipoReporte tipoReporte = enumTipoReporte.PRODUCTO;
/*  38: 59 */   private enumTipoValor tipoValor = enumTipoValor.CANTIDAD;
/*  39:    */   private boolean anuladas;
/*  40:    */   private boolean saldoInicial;
/*  41:    */   private List<SelectItem> listaTipoReporte;
/*  42:    */   private List<SelectItem> listaTipoValor;
/*  43:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  44:    */   
/*  45:    */   private static enum enumTipoReporte
/*  46:    */   {
/*  47: 65 */     PRODUCTO,  PROVEEDOR;
/*  48:    */     
/*  49:    */     private enumTipoReporte() {}
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static enum enumTipoValor
/*  53:    */   {
/*  54: 70 */     CANTIDAD,  PRECIO;
/*  55:    */     
/*  56:    */     private enumTipoValor() {}
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected JRDataSource getJRDataSource()
/*  60:    */   {
/*  61: 86 */     List listaDatosReporte = new ArrayList();
/*  62: 87 */     JRDataSource ds = null;
/*  63:    */     String[] fields;
/*  64:    */     String[] fields;
/*  65: 89 */     if (this.tipoReporte == enumTipoReporte.PRODUCTO)
/*  66:    */     {
/*  67: 90 */       listaDatosReporte = this.servicioReporteCompra.getReporteAnalisisComprasProducto(this.fechaDesde, this.fechaHasta, this.saldoInicial, AppUtil.getOrganizacion().getId(), 
/*  68: 91 */         getSubcategoriaProducto().getId(), isIndicadorCantidad());
/*  69: 92 */       fields = new String[] { "f_nombreEmpresa", "f_codigoProducto", "f_codigoComercial", "f_nombreProducto", "f_unidad", "f_cantidad", "f_mes" };
/*  70:    */     }
/*  71:    */     else
/*  72:    */     {
/*  73: 94 */       listaDatosReporte = this.servicioReporteCompra.getReporteAnalisisComprasProveedor(this.fechaDesde, this.fechaHasta, this.saldoInicial, 
/*  74: 95 */         AppUtil.getOrganizacion().getId(), getSubcategoriaProducto().getId(), isIndicadorCantidad());
/*  75: 96 */       fields = new String[] { "f_nombreEmpresa", "f_identificacionCliente", "f_codigoProducto", "f_codigoComercial", "f_nombreProducto", "f_unidad", "f_cantidad" };
/*  76:    */     }
/*  77:100 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  78:    */     
/*  79:102 */     return ds;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected String getCompileFileName()
/*  83:    */   {
/*  84:112 */     if (this.tipoReporte.equals(enumTipoReporte.PRODUCTO)) {
/*  85:113 */       return "reporteAnalisisProducto";
/*  86:    */     }
/*  87:115 */     return "reporteAnalisisEmpresa";
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected Map<String, Object> getReportParameters()
/*  91:    */   {
/*  92:126 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  93:127 */     reportParameters.put("ReportTitle", "Facturacion resumido");
/*  94:128 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/*  95:129 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/*  96:130 */     reportParameters.put("p_formatoFecha", getFormatoFecha());
/*  97:131 */     return reportParameters;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String execute()
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:141 */       super.prepareReport();
/* 105:    */     }
/* 106:    */     catch (JRException e)
/* 107:    */     {
/* 108:143 */       LOG.info("Error JRException");
/* 109:144 */       e.printStackTrace();
/* 110:145 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 111:    */     }
/* 112:    */     catch (IOException e)
/* 113:    */     {
/* 114:147 */       LOG.info("Error IOException");
/* 115:148 */       e.printStackTrace();
/* 116:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 117:    */     }
/* 118:152 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Date getFechaDesde()
/* 122:    */   {
/* 123:161 */     return this.fechaDesde;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setFechaDesde(Date fechaDesde)
/* 127:    */   {
/* 128:171 */     this.fechaDesde = fechaDesde;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Date getFechaHasta()
/* 132:    */   {
/* 133:180 */     return this.fechaHasta;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setFechaHasta(Date fechaHasta)
/* 137:    */   {
/* 138:190 */     this.fechaHasta = fechaHasta;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 142:    */   {
/* 143:199 */     if (this.subcategoriaProducto == null) {
/* 144:200 */       this.subcategoriaProducto = new SubcategoriaProducto();
/* 145:    */     }
/* 146:202 */     return this.subcategoriaProducto;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 150:    */   {
/* 151:212 */     this.subcategoriaProducto = subcategoriaProducto;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isAnuladas()
/* 155:    */   {
/* 156:221 */     return this.anuladas;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setAnuladas(boolean anuladas)
/* 160:    */   {
/* 161:231 */     this.anuladas = anuladas;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean isSaldoInicial()
/* 165:    */   {
/* 166:240 */     return this.saldoInicial;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setSaldoInicial(boolean saldoInicial)
/* 170:    */   {
/* 171:250 */     this.saldoInicial = saldoInicial;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public enumTipoReporte getTipoReporte()
/* 175:    */   {
/* 176:259 */     return this.tipoReporte;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setTipoReporte(enumTipoReporte tipoReporte)
/* 180:    */   {
/* 181:269 */     this.tipoReporte = tipoReporte;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public enumTipoValor getTipoValor()
/* 185:    */   {
/* 186:278 */     return this.tipoValor;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setTipoValor(enumTipoValor tipoValor)
/* 190:    */   {
/* 191:288 */     this.tipoValor = tipoValor;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<SelectItem> getListaTipoReporte()
/* 195:    */   {
/* 196:297 */     if (this.listaTipoReporte == null)
/* 197:    */     {
/* 198:298 */       this.listaTipoReporte = new ArrayList();
/* 199:299 */       for (enumTipoReporte tr : enumTipoReporte.values()) {
/* 200:300 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 201:    */       }
/* 202:    */     }
/* 203:303 */     return this.listaTipoReporte;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 207:    */   {
/* 208:313 */     this.listaTipoReporte = listaTipoReporte;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public List<SelectItem> getListaTipoValor()
/* 212:    */   {
/* 213:322 */     if (this.listaTipoValor == null)
/* 214:    */     {
/* 215:323 */       this.listaTipoValor = new ArrayList();
/* 216:324 */       for (enumTipoValor tv : enumTipoValor.values()) {
/* 217:325 */         this.listaTipoValor.add(new SelectItem(tv, tv.name()));
/* 218:    */       }
/* 219:    */     }
/* 220:328 */     return this.listaTipoValor;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setListaTipoValor(List<SelectItem> listaTipoValor)
/* 224:    */   {
/* 225:338 */     this.listaTipoValor = listaTipoValor;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 229:    */   {
/* 230:347 */     if (this.listaSubcategoriaProducto == null) {
/* 231:348 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 232:    */     }
/* 233:350 */     return this.listaSubcategoriaProducto;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 237:    */   {
/* 238:360 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean isMostrarFechas()
/* 242:    */   {
/* 243:364 */     return this.tipoReporte != enumTipoReporte.PRODUCTO;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public boolean isIndicadorCantidad()
/* 247:    */   {
/* 248:368 */     return getTipoValor() == enumTipoValor.CANTIDAD;
/* 249:    */   }
/* 250:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReporteAnalisisComprasBean
 * JD-Core Version:    0.7.0.1
 */