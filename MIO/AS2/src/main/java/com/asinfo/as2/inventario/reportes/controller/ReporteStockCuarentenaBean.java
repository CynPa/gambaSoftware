/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  16:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  27:    */ import net.sf.jasperreports.engine.JRException;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.event.SelectEvent;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ReporteStockCuarentenaBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private transient ServicioProducto servicioProducto;
/*  39:    */   @EJB
/*  40:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  41:    */   @EJB
/*  42:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  43:    */   @EJB
/*  44:    */   private ServicioRegistroPeso servicioRegistroPeso;
/*  45:    */   @EJB
/*  46:    */   private ServicioEmpresa servicioEmpresa;
/*  47:    */   @EJB
/*  48:    */   private ServicioPedidoProveedor servicioPedidoProveedor;
/*  49: 67 */   private Date fechaHasta = new Date();
/*  50:    */   private PedidoProveedor pedidoProveedor;
/*  51:    */   private List<PedidoProveedor> listaPedidoProveedor;
/*  52:    */   private Empresa empresa;
/*  53:    */   private Producto producto;
/*  54:    */   
/*  55:    */   protected JRDataSource getJRDataSource()
/*  56:    */   {
/*  57: 87 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  58: 88 */     JRDataSource ds = null;
/*  59:    */     try
/*  60:    */     {
/*  61: 91 */       listaDatosReporte = this.servicioRegistroPeso.getReporteProductoCuarentena(this.fechaHasta, this.empresa, this.producto, this.pedidoProveedor);
/*  62:    */       
/*  63: 93 */       String[] fields = { "f_fecha", "f_registroPeso", "f_ordenCompra", "f_identificacion", "f_empresa", "f_codigoProducto", "f_nombreProducto", "f_cantidad", "f_pesoNeto" };
/*  64:    */       
/*  65:    */ 
/*  66: 96 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  67:    */     }
/*  68:    */     catch (Exception e)
/*  69:    */     {
/*  70:101 */       e.printStackTrace();
/*  71:    */     }
/*  72:104 */     return ds;
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected String getCompileFileName()
/*  76:    */   {
/*  77:110 */     return "reporteProductoCuarentena";
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected Map<String, Object> getReportParameters()
/*  81:    */   {
/*  82:116 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  83:117 */     reportParameters.put("ReportTitle", "Stock Cuarentena");
/*  84:118 */     reportParameters.put("usuario", "Usuario:");
/*  85:119 */     reportParameters.put("pagina", "Pagina:");
/*  86:120 */     reportParameters.put("hasta", "Hasta:");
/*  87:121 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  88:122 */     return reportParameters;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String execute()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:133 */       super.prepareReport();
/*  96:    */     }
/*  97:    */     catch (JRException e)
/*  98:    */     {
/*  99:136 */       LOG.info("Error JRException");
/* 100:137 */       e.printStackTrace();
/* 101:138 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 102:    */     }
/* 103:    */     catch (IOException e)
/* 104:    */     {
/* 105:140 */       LOG.info("Error IOException");
/* 106:141 */       e.printStackTrace();
/* 107:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 108:    */     }
/* 109:144 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 113:    */   {
/* 114:148 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void cargarProducto(Producto producto)
/* 118:    */   {
/* 119:152 */     this.producto = producto;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void obtenerPedidosProveedor(SelectEvent event)
/* 123:    */   {
/* 124:161 */     Empresa proveedorAux = this.servicioEmpresa.obtenerDatosProveedor(((Empresa)event.getObject()).getIdEmpresa());
/* 125:162 */     setEmpresa(proveedorAux);
/* 126:164 */     if (this.listaPedidoProveedor == null) {
/* 127:165 */       this.listaPedidoProveedor = new ArrayList();
/* 128:    */     }
/* 129:167 */     this.listaPedidoProveedor.clear();
/* 130:169 */     if (getEmpresa() != null)
/* 131:    */     {
/* 132:170 */       HashMap<String, String> filters = new HashMap();
/* 133:171 */       filters.put("empresa.idEmpresa", String.valueOf(getEmpresa().getId()));
/* 134:172 */       filters.put("estado", "!=" + Estado.ANULADO.toString());
/* 135:173 */       this.listaPedidoProveedor = this.servicioPedidoProveedor.obtenerListaCombo("numero", true, filters);
/* 136:175 */       if ((getPedidoProveedor() != null) && (!this.listaPedidoProveedor.contains(getPedidoProveedor()))) {
/* 137:176 */         this.listaPedidoProveedor.add(getPedidoProveedor());
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Date getFechaHasta()
/* 143:    */   {
/* 144:187 */     return this.fechaHasta;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setFechaHasta(Date fechaHasta)
/* 148:    */   {
/* 149:197 */     this.fechaHasta = fechaHasta;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Empresa getEmpresa()
/* 153:    */   {
/* 154:201 */     return this.empresa;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setEmpresa(Empresa empresa)
/* 158:    */   {
/* 159:205 */     this.empresa = empresa;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Producto getProducto()
/* 163:    */   {
/* 164:209 */     return this.producto;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setProducto(Producto producto)
/* 168:    */   {
/* 169:213 */     this.producto = producto;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void eliminarProducto()
/* 173:    */   {
/* 174:217 */     setProducto(null);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public PedidoProveedor getPedidoProveedor()
/* 178:    */   {
/* 179:221 */     return this.pedidoProveedor;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 183:    */   {
/* 184:225 */     this.pedidoProveedor = pedidoProveedor;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<PedidoProveedor> getListaPedidoProveedor()
/* 188:    */   {
/* 189:229 */     return this.listaPedidoProveedor;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setListaPedidoProveedor(List<PedidoProveedor> listaPedidoProveedor)
/* 193:    */   {
/* 194:233 */     this.listaPedidoProveedor = listaPedidoProveedor;
/* 195:    */   }
/* 196:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteStockCuarentenaBean
 * JD-Core Version:    0.7.0.1
 */