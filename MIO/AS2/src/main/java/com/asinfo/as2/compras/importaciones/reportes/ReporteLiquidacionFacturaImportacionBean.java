/*   1:    */ package com.asinfo.as2.compras.importaciones.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.importaciones.reportes.servicio.ServicioReporteImportacion;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*   7:    */ import com.asinfo.as2.controller.LanguageController;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  11:    */ import com.asinfo.as2.entities.Pais;
/*  12:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  16:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.io.PrintStream;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import javax.faces.model.SelectItem;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import net.sf.jasperreports.engine.JRException;
/*  30:    */ import org.primefaces.event.SelectEvent;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class ReporteLiquidacionFacturaImportacionBean
/*  35:    */   extends AbstractBaseReportBean
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -2302233523055404539L;
/*  38:    */   @EJB
/*  39:    */   private ServicioReporteImportacion servicioReporteImportacion;
/*  40:    */   @EJB
/*  41:    */   private ServicioEmpresa servicioEmpresa;
/*  42:    */   @EJB
/*  43:    */   private ServicioPais servicioPais;
/*  44:    */   @EJB
/*  45:    */   private ServicioFacturaProveedor servicioFacturaProveedor;
/*  46:    */   private Date fechaDesde;
/*  47:    */   private Date fechaHasta;
/*  48:    */   private Empresa proveedor;
/*  49:    */   private Pais paisOrigen;
/*  50:    */   private FacturaProveedor facturaProveedor;
/*  51:    */   private Empresa empresa;
/*  52:    */   private EnumTipoReporte tipoReporte;
/*  53:    */   private List<Pais> listaPais;
/*  54:    */   private List<SelectItem> listaTipoReporte;
/*  55:    */   
/*  56:    */   private static enum EnumTipoReporte
/*  57:    */   {
/*  58: 78 */     IMPORTACION,  PROVEEDOR;
/*  59:    */     
/*  60:    */     private EnumTipoReporte() {}
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected JRDataSource getJRDataSource()
/*  64:    */   {
/*  65: 89 */     JRDataSource ds = null;
/*  66:    */     try
/*  67:    */     {
/*  68: 91 */       List listaReporte = new ArrayList();
/*  69: 92 */       listaReporte = this.servicioReporteImportacion.getReporteLiquidacionFacturaImportacion(this.fechaDesde, this.fechaHasta, getProveedor().getId(), 
/*  70: 93 */         getPaisOrigen().getId(), getFacturaProveedor().getId());
/*  71: 94 */       String[] fields = { "fppNumero", "fppFecha", "fppEstado", "fppProveedor", "fppIdentificacionProveedor", "fppTotal", "fppDescuento", "fppImpuesto", "fpiFechaEmbarque", "fpiPuertoEmbarque", "fpiFechaLlegada", "fpiPuertoLlegada", "fpiMedioTransporteEnum", "pais", "fphNumero", "fphFecha", "fphEstado", "fphProveedor", "fphIdentificacionProveedor", "fphTotal", "fphDescuento", "fphImpuesto", "pfphCodigo", "pfphCodigoComercial", "pfphNombre", "uCodigo", "dfphCantidad", "dfphPrecio", "dfphDescuento", "dfphDescripcion", "gastoImportacion", "pfphEstablecimiento", "pfphPuntoEmision", "pfphNumeroFactura", "pfphGrupoGasto", "valorGasto", "f_descripcion", "f_informacionTransporte", "f_descripcionImportacion", "facturaProveedor", "f_numeroDUI" };
/*  72:    */       
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:101 */       ds = new QueryResultDataSource(listaReporte, fields);
/*  79:    */     }
/*  80:    */     catch (ExcepcionAS2Compras e1)
/*  81:    */     {
/*  82:104 */       e1.printStackTrace();
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:106 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos") + ">>>>>>");
/*  87:    */     }
/*  88:109 */     return ds;
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected Map<String, Object> getReportParameters()
/*  92:    */   {
/*  93:120 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  94:121 */     reportParameters.put("ReportTitle", "Liquidacion Factura Importacion");
/*  95:122 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  96:123 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  97:124 */     if (this.facturaProveedor != null) {
/*  98:125 */       reportParameters.put("p_indicadorImpresion", "true");
/*  99:    */     } else {
/* 100:127 */       reportParameters.put("p_indicadorImpresion", "false");
/* 101:    */     }
/* 102:129 */     if (this.tipoReporte.equals(EnumTipoReporte.IMPORTACION)) {
/* 103:130 */       reportParameters.put("SUBREPORT_DIR", getPathReportes());
/* 104:    */     }
/* 105:132 */     return reportParameters;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected String getCompileFileName()
/* 109:    */   {
/* 110:142 */     if (this.tipoReporte.equals(EnumTipoReporte.IMPORTACION)) {
/* 111:143 */       return "reporteLiquidacionFacturaImportacion";
/* 112:    */     }
/* 113:145 */     return "reporteLiquidacionFacturaImportacionProveedor";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String execute()
/* 117:    */   {
/* 118:    */     try
/* 119:    */     {
/* 120:156 */       validaFechas();
/* 121:157 */       super.prepareReport();
/* 122:    */     }
/* 123:    */     catch (JRException e)
/* 124:    */     {
/* 125:160 */       e.printStackTrace();
/* 126:161 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos" + e.getMessage() + "1"));
/* 127:    */     }
/* 128:    */     catch (IOException e)
/* 129:    */     {
/* 130:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos" + e.getMessage() + "2"));
/* 131:    */     }
/* 132:165 */     return "";
/* 133:    */   }
/* 134:    */   
/* 135:    */   private void validaFechas()
/* 136:    */   {
/* 137:169 */     if (this.fechaDesde == null) {
/* 138:170 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 139:    */     }
/* 140:172 */     if (this.fechaHasta == null) {
/* 141:173 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 146:    */   {
/* 147:178 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void cargarProveedor(SelectEvent event)
/* 151:    */   {
/* 152:183 */     Empresa empresaAux = (Empresa)event.getObject();
/* 153:184 */     System.out.println(empresaAux.getNombreFiscal());
/* 154:185 */     this.empresa = empresaAux;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List<FacturaProveedor> autocompletarFacturasProveedor(String consulta)
/* 158:    */   {
/* 159:190 */     Map<String, String> filters = new HashMap();
/* 160:191 */     List<FacturaProveedor> lista = new ArrayList();
/* 161:193 */     if (this.empresa != null)
/* 162:    */     {
/* 163:194 */       filters.put("empresa.idEmpresa", "" + getEmpresa().getIdEmpresa());
/* 164:195 */       filters.put("documento.indicadorDocumentoExterior", "true");
/* 165:196 */       filters.put("documento.documentoBase", DocumentoBase.PEDIDO_IMPORTACION.toString());
/* 166:197 */       filters.put("estado", "!=" + Estado.ANULADO.toString());
/* 167:198 */       lista = this.servicioFacturaProveedor.obtenerListaComboConEqual("fecha", true, filters);
/* 168:    */     }
/* 169:    */     else
/* 170:    */     {
/* 171:201 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 172:    */     }
/* 173:204 */     return lista;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Date getFechaDesde()
/* 177:    */   {
/* 178:216 */     return this.fechaDesde;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setFechaDesde(Date fechaDesde)
/* 182:    */   {
/* 183:226 */     this.fechaDesde = fechaDesde;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Date getFechaHasta()
/* 187:    */   {
/* 188:235 */     return this.fechaHasta;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setFechaHasta(Date fechaHasta)
/* 192:    */   {
/* 193:245 */     this.fechaHasta = fechaHasta;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Empresa getProveedor()
/* 197:    */   {
/* 198:254 */     if (this.proveedor == null) {
/* 199:255 */       this.proveedor = new Empresa();
/* 200:    */     }
/* 201:257 */     return this.proveedor;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setProveedor(Empresa proveedor)
/* 205:    */   {
/* 206:267 */     if (proveedor == null) {
/* 207:268 */       proveedor = new Empresa();
/* 208:    */     }
/* 209:270 */     this.proveedor = proveedor;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Pais getPaisOrigen()
/* 213:    */   {
/* 214:279 */     if (this.paisOrigen == null) {
/* 215:280 */       this.paisOrigen = new Pais();
/* 216:    */     }
/* 217:282 */     return this.paisOrigen;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setPaisOrigen(Pais paisOrigen)
/* 221:    */   {
/* 222:292 */     this.paisOrigen = paisOrigen;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List<Pais> getListaPais()
/* 226:    */   {
/* 227:296 */     if (this.listaPais == null) {
/* 228:297 */       this.listaPais = this.servicioPais.obtenerListaCombo("nombre", true, null);
/* 229:    */     }
/* 230:299 */     return this.listaPais;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public FacturaProveedor getFacturaProveedor()
/* 234:    */   {
/* 235:308 */     if (this.facturaProveedor == null) {
/* 236:309 */       this.facturaProveedor = new FacturaProveedor();
/* 237:    */     }
/* 238:311 */     return this.facturaProveedor;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/* 242:    */   {
/* 243:321 */     this.facturaProveedor = facturaProveedor;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Empresa getEmpresa()
/* 247:    */   {
/* 248:325 */     return this.empresa;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setEmpresa(Empresa empresa)
/* 252:    */   {
/* 253:329 */     this.empresa = empresa;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public EnumTipoReporte getTipoReporte()
/* 257:    */   {
/* 258:336 */     return this.tipoReporte;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setTipoReporte(EnumTipoReporte tipoReporte)
/* 262:    */   {
/* 263:344 */     this.tipoReporte = tipoReporte;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public List<SelectItem> getListaTipoReporte()
/* 267:    */   {
/* 268:351 */     if (this.listaTipoReporte == null)
/* 269:    */     {
/* 270:352 */       this.listaTipoReporte = new ArrayList();
/* 271:353 */       for (EnumTipoReporte tr : EnumTipoReporte.values()) {
/* 272:354 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 273:    */       }
/* 274:    */     }
/* 275:358 */     return this.listaTipoReporte;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 279:    */   {
/* 280:366 */     this.listaTipoReporte = listaTipoReporte;
/* 281:    */   }
/* 282:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.reportes.ReporteLiquidacionFacturaImportacionBean
 * JD-Core Version:    0.7.0.1
 */