/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   4:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReportePedidoRecepcionFactura;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Collection;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import java.util.TreeMap;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.JRException;
/*  27:    */ import org.primefaces.event.SelectEvent;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ReportePedidoRecepcionFacturaBean
/*  32:    */   extends AbstractBaseReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 7231888205008181204L;
/*  35:    */   @EJB
/*  36:    */   private ServicioReportePedidoRecepcionFactura servicioReportePedidoRecepcionFactura;
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpresa servicioEmpresa;
/*  39:    */   @EJB
/*  40:    */   private ServicioPedidoProveedor servicioPedidoProveedor;
/*  41:    */   private Date fechaDesde;
/*  42:    */   private Date fechaHasta;
/*  43:    */   private Empresa proveedor;
/*  44:    */   private PedidoProveedor pedidoProveedor;
/*  45:    */   private boolean indicadorReporteDetallado;
/*  46:    */   private List<PedidoProveedor> listaPedidoProveedor;
/*  47: 71 */   private String COMPILE_FILE_NAME = "reportePedidoRecepcionFactura";
/*  48:    */   
/*  49:    */   protected JRDataSource getJRDataSource()
/*  50:    */   {
/*  51: 77 */     List<Object[]> listaDatosReportePedidoRecepcionFactura = new ArrayList();
/*  52: 78 */     List<Object[]> listaDatosReporteRecepcionSinFactura = new ArrayList();
/*  53: 79 */     List<Object[]> listaDatosReportePedidoConFactura = new ArrayList();
/*  54:    */     
/*  55: 81 */     JRDataSource ds = null;
/*  56:    */     try
/*  57:    */     {
/*  58: 85 */       listaDatosReportePedidoRecepcionFactura = this.servicioReportePedidoRecepcionFactura.getReportePedidoRecepcionFactura(this.pedidoProveedor, this.fechaDesde, this.fechaHasta, this.proveedor);
/*  59:    */       
/*  60:    */ 
/*  61: 88 */       listaDatosReporteRecepcionSinFactura = this.servicioReportePedidoRecepcionFactura.getReporteRecepcionSinFactura(this.pedidoProveedor, this.fechaDesde, this.fechaHasta, this.proveedor);
/*  62:    */       
/*  63:    */ 
/*  64: 91 */       listaDatosReportePedidoConFactura = this.servicioReportePedidoRecepcionFactura.getReportePedidoConFactura(this.pedidoProveedor, this.fechaDesde, this.fechaHasta, this.proveedor);
/*  65:    */       
/*  66:    */ 
/*  67: 94 */       Collection coleccionAuxiliar = obtenerListaOrdenada(listaDatosReportePedidoRecepcionFactura, listaDatosReporteRecepcionSinFactura, listaDatosReportePedidoConFactura);
/*  68:    */       
/*  69: 96 */       List listaDatosReporte = new ArrayList();
/*  70: 97 */       listaDatosReporte.addAll(coleccionAuxiliar);
/*  71:    */       
/*  72: 99 */       String[] fields = { "numeroFactura", "fechaFactura", "numeroRecepcion", "fechaRecepcion", "cantidadFacturada", "cantidadRecibida", "cantidadPedida", "numeroPedido", "fechaPedido", "codigoProducto", "nombreProducto", "nombreFiscal" };
/*  73:    */       
/*  74:    */ 
/*  75:102 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  76:    */     }
/*  77:    */     catch (ExcepcionAS2 e)
/*  78:    */     {
/*  79:104 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  80:    */     }
/*  81:110 */     return ds;
/*  82:    */   }
/*  83:    */   
/*  84:    */   private Collection obtenerListaOrdenada(List<Object[]> listaDatosReportePedidoRecepcionFactura, List<Object[]> listaDatosReporteRecepcionSinFactura, List<Object[]> listaDatosReportePedidoConFactura)
/*  85:    */   {
/*  86:122 */     Map<String, Object> tmReporte = new TreeMap();
/*  87:124 */     for (Object[] object : listaDatosReportePedidoRecepcionFactura)
/*  88:    */     {
/*  89:126 */       String clave = String.valueOf((Integer)object[14]) + ":" + String.valueOf((Integer)object[15]) + ":" + String.valueOf((Integer)object[12]) + ":" + String.valueOf((Integer)object[13]);
/*  90:127 */       tmReporte.put(clave, object);
/*  91:    */     }
/*  92:131 */     for (Object[] object : listaDatosReporteRecepcionSinFactura)
/*  93:    */     {
/*  94:133 */       String clave = String.valueOf((Integer)object[14]) + ":" + String.valueOf((Integer)object[15]) + ":" + String.valueOf((Integer)object[12]) + ":" + String.valueOf((Integer)object[13]);
/*  95:134 */       if (!tmReporte.containsKey(clave)) {
/*  96:135 */         tmReporte.put(clave, object);
/*  97:    */       }
/*  98:    */     }
/*  99:141 */     for (Object[] object : listaDatosReportePedidoConFactura)
/* 100:    */     {
/* 101:144 */       String clave = String.valueOf((Integer)object[16]) + ":" + String.valueOf((Integer)object[17]) + ":" + String.valueOf((Integer)object[14]) + ":" + String.valueOf((Integer)object[15]) + ":" + String.valueOf((Integer)object[12]) + ":" + String.valueOf((Integer)object[13]);
/* 102:145 */       if (!tmReporte.containsKey(clave)) {
/* 103:146 */         tmReporte.put(clave, object);
/* 104:    */       }
/* 105:    */     }
/* 106:149 */     return tmReporte.values();
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected Map<String, Object> getReportParameters()
/* 110:    */   {
/* 111:160 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 112:161 */     reportParameters.put("ReportTitle", "Pedidos, Recepcion y Factura");
/* 113:162 */     return reportParameters;
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected String getCompileFileName()
/* 117:    */   {
/* 118:172 */     if (this.indicadorReporteDetallado) {
/* 119:173 */       this.COMPILE_FILE_NAME = "reportePedidoRecepcionFacturaDetallado";
/* 120:    */     }
/* 121:175 */     return this.COMPILE_FILE_NAME;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String execute()
/* 125:    */   {
/* 126:    */     try
/* 127:    */     {
/* 128:180 */       validaFechas();
/* 129:181 */       super.prepareReport();
/* 130:    */     }
/* 131:    */     catch (JRException e)
/* 132:    */     {
/* 133:184 */       e.printStackTrace();
/* 134:185 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 135:    */     }
/* 136:    */     catch (IOException e)
/* 137:    */     {
/* 138:187 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 139:    */     }
/* 140:189 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 144:    */   {
/* 145:199 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 146:    */   }
/* 147:    */   
/* 148:    */   private void validaFechas()
/* 149:    */   {
/* 150:206 */     if (this.fechaDesde == null) {
/* 151:207 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 152:    */     }
/* 153:209 */     if (this.fechaHasta == null) {
/* 154:210 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void obtenerPedidosProveedor(SelectEvent event)
/* 159:    */   {
/* 160:220 */     Empresa proveedorAux = this.servicioEmpresa.obtenerDatosProveedor(((Empresa)event.getObject()).getIdEmpresa());
/* 161:221 */     setProveedor(proveedorAux);
/* 162:223 */     if (this.listaPedidoProveedor == null) {
/* 163:224 */       this.listaPedidoProveedor = new ArrayList();
/* 164:    */     }
/* 165:226 */     this.listaPedidoProveedor.clear();
/* 166:228 */     if (getProveedor() != null)
/* 167:    */     {
/* 168:229 */       HashMap<String, String> filters = new HashMap();
/* 169:230 */       filters.put("empresa.idEmpresa", String.valueOf(getProveedor().getId()));
/* 170:231 */       filters.put("estado", "!=" + Estado.ANULADO.toString());
/* 171:232 */       this.listaPedidoProveedor = this.servicioPedidoProveedor.obtenerListaCombo("numero", true, filters);
/* 172:234 */       if ((getPedidoProveedor() != null) && (!this.listaPedidoProveedor.contains(getPedidoProveedor()))) {
/* 173:235 */         this.listaPedidoProveedor.add(getPedidoProveedor());
/* 174:    */       }
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Date getFechaDesde()
/* 179:    */   {
/* 180:248 */     return this.fechaDesde;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setFechaDesde(Date fechaDesde)
/* 184:    */   {
/* 185:258 */     this.fechaDesde = fechaDesde;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Date getFechaHasta()
/* 189:    */   {
/* 190:267 */     return this.fechaHasta;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setFechaHasta(Date fechaHasta)
/* 194:    */   {
/* 195:277 */     this.fechaHasta = fechaHasta;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Empresa getProveedor()
/* 199:    */   {
/* 200:286 */     return this.proveedor;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setProveedor(Empresa proveedor)
/* 204:    */   {
/* 205:296 */     this.proveedor = proveedor;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isIndicadorReporteDetallado()
/* 209:    */   {
/* 210:305 */     return this.indicadorReporteDetallado;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setIndicadorReporteDetallado(boolean indicadorReporteDetallado)
/* 214:    */   {
/* 215:315 */     this.indicadorReporteDetallado = indicadorReporteDetallado;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public PedidoProveedor getPedidoProveedor()
/* 219:    */   {
/* 220:324 */     return this.pedidoProveedor;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 224:    */   {
/* 225:334 */     this.pedidoProveedor = pedidoProveedor;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<PedidoProveedor> getListaPedidoProveedor()
/* 229:    */   {
/* 230:338 */     return this.listaPedidoProveedor;
/* 231:    */   }
/* 232:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReportePedidoRecepcionFacturaBean
 * JD-Core Version:    0.7.0.1
 */