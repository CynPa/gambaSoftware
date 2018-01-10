/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteInventarioProducto;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Bodega;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  16:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  20:    */ import java.io.IOException;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import javax.persistence.Temporal;
/*  30:    */ import javax.persistence.TemporalType;
/*  31:    */ import javax.validation.constraints.NotNull;
/*  32:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  33:    */ import net.sf.jasperreports.engine.JRException;
/*  34:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ 
/*  37:    */ @ManagedBean
/*  38:    */ @ViewScoped
/*  39:    */ public class ReporteInventarioProyectoBean
/*  40:    */   extends AbstractBaseReportBean
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 1L;
/*  43:    */   @EJB
/*  44:    */   private ServicioBodega servicioBodega;
/*  45:    */   @EJB
/*  46:    */   private ServicioProducto servicioProducto;
/*  47:    */   @EJB
/*  48:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  49:    */   @EJB
/*  50:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  51:    */   @EJB
/*  52:    */   private ServicioInventarioProducto servicioInventarioProducto;
/*  53:    */   private Bodega bodega;
/*  54:    */   private DimensionContable proyecto;
/*  55:    */   @Temporal(TemporalType.DATE)
/*  56:    */   @NotNull
/*  57: 78 */   private Date fechaHasta = new Date();
/*  58:    */   private Producto producto;
/*  59:    */   private List<Bodega> listaBodega;
/*  60:    */   private List<ReporteInventarioProducto> listaReporteInventarioProducto;
/*  61:    */   private HashMap<Integer, Producto> hmProducto;
/*  62:    */   
/*  63:    */   protected JRDataSource getJRDataSource()
/*  64:    */   {
/*  65:100 */     validaDatos();
/*  66:    */     
/*  67:102 */     Date fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/*  68:103 */     this.listaReporteInventarioProducto = this.servicioInventarioProducto.obtenerMovimientosInventarioProducto(AppUtil.getOrganizacion().getId(), this.producto, this.bodega, fechaDesde, this.fechaHasta, null, this.proyecto, 
/*  69:104 */       AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/*  70:    */     
/*  71:106 */     JRDataSource ds = new JRBeanCollectionDataSource(this.listaReporteInventarioProducto);
/*  72:    */     
/*  73:108 */     return ds;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected String getCompileFileName()
/*  77:    */   {
/*  78:119 */     return "reporteInventarioProyecto";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void cargarProducto(Producto producto)
/*  82:    */   {
/*  83:124 */     this.producto = producto;
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected Map<String, Object> getReportParameters()
/*  87:    */   {
/*  88:136 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  89:137 */     reportParameters.put("ReportTitle", "Inventario Producto (Proyecto)");
/*  90:138 */     reportParameters.put("fechaHasta", getFechaHasta());
/*  91:139 */     reportParameters.put("p_proyecto", this.proyecto == null ? "TODOS" : this.proyecto.getNombre());
/*  92:140 */     reportParameters.put("p_bodega", this.bodega != null ? this.bodega.getNombre() : "Todos");
/*  93:141 */     reportParameters.put("p_producto", this.producto != null ? this.producto.getNombre() : "Todos");
/*  94:    */     
/*  95:143 */     return reportParameters;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String execute()
/*  99:    */   {
/* 100:    */     try
/* 101:    */     {
/* 102:153 */       super.prepareReport();
/* 103:    */     }
/* 104:    */     catch (JRException e)
/* 105:    */     {
/* 106:155 */       LOG.info("Error JRException");
/* 107:156 */       e.printStackTrace();
/* 108:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 109:    */     }
/* 110:    */     catch (IOException e)
/* 111:    */     {
/* 112:159 */       LOG.info("Error IOException");
/* 113:160 */       e.printStackTrace();
/* 114:161 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 115:    */     }
/* 116:163 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void validaDatos()
/* 120:    */   {
/* 121:168 */     if (this.fechaHasta == null) {
/* 122:169 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 127:    */   {
/* 128:174 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 129:    */     
/* 130:176 */     HashMap<String, String> filters = new HashMap();
/* 131:177 */     filters.put("nombre", "%" + consulta.trim());
/* 132:    */     
/* 133:179 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 134:    */     
/* 135:181 */     return lista;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public List<CategoriaProducto> autocompletarCategoriaProducto(String consulta)
/* 139:    */   {
/* 140:185 */     List<CategoriaProducto> lista = new ArrayList();
/* 141:    */     
/* 142:187 */     HashMap<String, String> filters = new HashMap();
/* 143:188 */     filters.put("nombre", "%" + consulta.trim());
/* 144:    */     
/* 145:190 */     lista = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 146:    */     
/* 147:192 */     return lista;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Date getFechaHasta()
/* 151:    */   {
/* 152:201 */     return this.fechaHasta;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setFechaHasta(Date fechaHasta)
/* 156:    */   {
/* 157:211 */     this.fechaHasta = fechaHasta;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Bodega getBodega()
/* 161:    */   {
/* 162:220 */     return this.bodega;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setBodega(Bodega bodega)
/* 166:    */   {
/* 167:230 */     this.bodega = bodega;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<Bodega> getListaBodega()
/* 171:    */   {
/* 172:239 */     if (this.listaBodega == null) {
/* 173:240 */       this.listaBodega = this.servicioBodega.obtenerListaCombo("nombre", true, null);
/* 174:    */     }
/* 175:242 */     return this.listaBodega;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 179:    */   {
/* 180:252 */     this.listaBodega = listaBodega;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List<ReporteInventarioProducto> getListaReporteInventarioProducto()
/* 184:    */   {
/* 185:261 */     return this.listaReporteInventarioProducto;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setListaReporteInventarioProducto(List<ReporteInventarioProducto> listaReporteInventarioProducto)
/* 189:    */   {
/* 190:271 */     this.listaReporteInventarioProducto = listaReporteInventarioProducto;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public HashMap<Integer, Producto> getHmProducto()
/* 194:    */   {
/* 195:280 */     return this.hmProducto;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setHmProducto(HashMap<Integer, Producto> hmProducto)
/* 199:    */   {
/* 200:290 */     this.hmProducto = hmProducto;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public DimensionContable getProyecto()
/* 204:    */   {
/* 205:297 */     return this.proyecto;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setProyecto(DimensionContable proyecto)
/* 209:    */   {
/* 210:305 */     this.proyecto = proyecto;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Producto getProducto()
/* 214:    */   {
/* 215:309 */     return this.producto;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setProducto(Producto producto)
/* 219:    */   {
/* 220:313 */     this.producto = producto;
/* 221:    */   }
/* 222:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteInventarioProyectoBean
 * JD-Core Version:    0.7.0.1
 */