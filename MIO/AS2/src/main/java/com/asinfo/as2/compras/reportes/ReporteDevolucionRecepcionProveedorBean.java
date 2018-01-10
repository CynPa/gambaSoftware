/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteDevolucionRecepcionProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.Proveedor;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  17:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  28:    */ import net.sf.jasperreports.engine.JRException;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ReporteDevolucionRecepcionProveedorBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 3615647209557356681L;
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpresa servicioEmpresa;
/*  39:    */   @EJB
/*  40:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  41:    */   @EJB
/*  42:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  43:    */   @EJB
/*  44:    */   private ServicioProducto servicioProducto;
/*  45:    */   @EJB
/*  46:    */   private ServicioReporteDevolucionRecepcionProveedor servicioReporteDevolucionRecepcionProveedor;
/*  47: 73 */   private Date fechaDesde = new Date();
/*  48: 74 */   private Date fechaHasta = new Date();
/*  49:    */   private Proveedor proveedor;
/*  50:    */   private CategoriaProducto categoriaProducto;
/*  51:    */   private SubcategoriaProducto subcategoriaProducto;
/*  52:    */   private Producto producto;
/*  53:    */   private Empresa empresa;
/*  54:    */   
/*  55:    */   protected JRDataSource getJRDataSource()
/*  56:    */   {
/*  57: 90 */     List listaDatosReporte = new ArrayList();
/*  58: 91 */     String[] fields = new String[0];
/*  59: 92 */     fields = new String[] { "f_fecha", "f_codigoProvedor", "f_nombreProveedor", "f_lote", "f_codigoProducto", "f_nombreProducto", "f_numeroDocumento", "f_nota", "f_recepcionCantidad", "f_recepcionCantidadInformativa", "f_recepcionPrecioUnitario", "f_recepcionTotal", "f_devolucionNumeroDocumento", "f_devolucionCantidad", "f_devolucionCantidadInformativa", "f_devolucionTotal" };
/*  60:    */     
/*  61:    */ 
/*  62:    */ 
/*  63: 96 */     listaDatosReporte = this.servicioReporteDevolucionRecepcionProveedor.getReporteDevolucionRecepcionProveedor(AppUtil.getOrganizacion().getId(), getEmpresa(), 
/*  64: 97 */       getCategoriaProducto(), getSubcategoriaProducto(), getProducto(), this.fechaDesde, this.fechaHasta);
/*  65:    */     
/*  66: 99 */     return new QueryResultDataSource(listaDatosReporte, fields);
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected String getCompileFileName()
/*  70:    */   {
/*  71:109 */     return "reporteDevolucionRecepcionProveedor";
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected Map<String, Object> getReportParameters()
/*  75:    */   {
/*  76:119 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  77:120 */     reportParameters.put("p_proveedor", getEmpresa() == null ? "Todos" : getEmpresa().getNombreFiscal());
/*  78:121 */     reportParameters.put("p_categoriaProducto", getCategoriaProducto() == null ? "Todos" : getCategoriaProducto().getNombre());
/*  79:122 */     reportParameters.put("p_subcategoriaProducto", getSubcategoriaProducto() == null ? "Todos" : getSubcategoriaProducto().getNombre());
/*  80:123 */     reportParameters.put("p_producto", getProducto() == null ? "Todos" : getProducto().getNombre());
/*  81:124 */     return reportParameters;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String execute()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:134 */       super.prepareReport();
/*  89:    */     }
/*  90:    */     catch (JRException e)
/*  91:    */     {
/*  92:136 */       LOG.info("Error JRException");
/*  93:137 */       e.printStackTrace();
/*  94:138 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  95:    */     }
/*  96:    */     catch (IOException e)
/*  97:    */     {
/*  98:140 */       LOG.info("Error IOException");
/*  99:141 */       e.printStackTrace();
/* 100:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 101:    */     }
/* 102:145 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 106:    */   {
/* 107:149 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<CategoriaProducto> autocompleatarCategoriaProducto(String consulta)
/* 111:    */   {
/* 112:153 */     HashMap<String, String> filters = new HashMap();
/* 113:154 */     filters.put("nombre", "%" + consulta.trim());
/* 114:155 */     filters.put("activo", "true");
/* 115:156 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 116:157 */     return this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 120:    */   {
/* 121:161 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 122:162 */     lista = this.servicioCategoriaProducto.obtenerListaSubcategoriaProducto(AppUtil.getOrganizacion().getIdOrganizacion(), getCategoriaProducto(), consulta);
/* 123:    */     
/* 124:164 */     return lista;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<Producto> autocompletarProducto(String consulta)
/* 128:    */   {
/* 129:168 */     List<Producto> lista = new ArrayList();
/* 130:169 */     lista = this.servicioProducto.obtenerListaCombo(AppUtil.getOrganizacion().getIdOrganizacion(), consulta, getCategoriaProducto(), 
/* 131:170 */       getSubcategoriaProducto());
/* 132:171 */     return lista;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void limpiarSubcategoria()
/* 136:    */   {
/* 137:175 */     setSubcategoriaProducto(null);
/* 138:176 */     limpiarProducto();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void limpiarProducto()
/* 142:    */   {
/* 143:179 */     setProducto(null);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Date getFechaDesde()
/* 147:    */   {
/* 148:185 */     return this.fechaDesde;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setFechaDesde(Date fechaDesde)
/* 152:    */   {
/* 153:189 */     this.fechaDesde = fechaDesde;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Date getFechaHasta()
/* 157:    */   {
/* 158:193 */     return this.fechaHasta;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setFechaHasta(Date fechaHasta)
/* 162:    */   {
/* 163:197 */     this.fechaHasta = fechaHasta;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Proveedor getProveedor()
/* 167:    */   {
/* 168:201 */     return this.proveedor;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setProveedor(Proveedor proveedor)
/* 172:    */   {
/* 173:205 */     this.proveedor = proveedor;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public CategoriaProducto getCategoriaProducto()
/* 177:    */   {
/* 178:209 */     return this.categoriaProducto;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 182:    */   {
/* 183:213 */     this.categoriaProducto = categoriaProducto;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 187:    */   {
/* 188:217 */     return this.subcategoriaProducto;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 192:    */   {
/* 193:221 */     this.subcategoriaProducto = subcategoriaProducto;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Producto getProducto()
/* 197:    */   {
/* 198:225 */     return this.producto;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setProducto(Producto producto)
/* 202:    */   {
/* 203:229 */     this.producto = producto;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Empresa getEmpresa()
/* 207:    */   {
/* 208:233 */     return this.empresa;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setEmpresa(Empresa empresa)
/* 212:    */   {
/* 213:237 */     this.empresa = empresa;
/* 214:    */   }
/* 215:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReporteDevolucionRecepcionProveedorBean
 * JD-Core Version:    0.7.0.1
 */