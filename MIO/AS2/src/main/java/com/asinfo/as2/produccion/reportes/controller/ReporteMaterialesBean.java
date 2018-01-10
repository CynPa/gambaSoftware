/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.produccion.PlanProduccion;
/*   8:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*   9:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  10:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioPlanProduccion;
/*  11:    */ import com.asinfo.as2.produccion.reportes.servicio.ServicioReporteFabricacion;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  14:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.text.SimpleDateFormat;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.JRException;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ReporteMaterialesBean
/*  32:    */   extends AbstractBaseReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @EJB
/*  36:    */   private ServicioReporteFabricacion servicioReporteFabricacion;
/*  37:    */   @EJB
/*  38:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  39:    */   @EJB
/*  40:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  41:    */   @EJB
/*  42:    */   private ServicioPlanProduccion servicioPlanProduccion;
/*  43:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  44:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/*  45:    */   private PlanProduccion planProduccionSeleccionado;
/*  46:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  47:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  48:    */   private List<PlanProduccion> listaPlanProduccion;
/*  49:    */   
/*  50:    */   @PostConstruct
/*  51:    */   public void init()
/*  52:    */   {
/*  53: 76 */     if (getListaPlanProduccion().size() > 0) {
/*  54: 77 */       this.planProduccionSeleccionado = ((PlanProduccion)getListaPlanProduccion().get(0));
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected JRDataSource getJRDataSource()
/*  59:    */   {
/*  60: 89 */     List listaDatosReporte = new ArrayList();
/*  61: 90 */     JRDataSource ds = null;
/*  62:    */     try
/*  63:    */     {
/*  64: 92 */       listaDatosReporte = this.servicioReporteFabricacion.getReporteMateriales(this.planProduccionSeleccionado);
/*  65: 93 */       String[] fields = { "f_codigoProducto", "f_codigoBarrasProducto", "f_nombreProducto", "f_unidad", "f_presentacionProducto", "f_cantidad" };
/*  66:    */       
/*  67: 95 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  68:    */     }
/*  69:    */     catch (Exception e)
/*  70:    */     {
/*  71: 97 */       e.printStackTrace();
/*  72:    */     }
/*  73: 99 */     return ds;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected String getCompileFileName()
/*  77:    */   {
/*  78:109 */     return "reporteMateriales";
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected Map<String, Object> getReportParameters()
/*  82:    */   {
/*  83:119 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/*  84:120 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  85:121 */     reportParameters.put("ReportTitle", "Reporte  Materiales");
/*  86:122 */     if (this.planProduccionSeleccionado != null)
/*  87:    */     {
/*  88:123 */       reportParameters.put("fechaInicio", this.planProduccionSeleccionado.getFechaInicio());
/*  89:124 */       reportParameters.put("fechaFin", this.planProduccionSeleccionado.getFechaFin());
/*  90:    */     }
/*  91:126 */     if (this.categoriaProductoSeleccionado != null) {
/*  92:127 */       reportParameters.put("categoriaProducto", this.categoriaProductoSeleccionado.getNombre());
/*  93:    */     }
/*  94:129 */     if (this.subcategoriaProductoSeleccionado != null) {
/*  95:130 */       reportParameters.put("subcategoriaProducto", this.subcategoriaProductoSeleccionado.getNombre());
/*  96:    */     }
/*  97:132 */     return reportParameters;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String execute()
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:142 */       super.prepareReport();
/* 105:    */     }
/* 106:    */     catch (JRException e)
/* 107:    */     {
/* 108:144 */       LOG.info("Error JRException");
/* 109:145 */       e.printStackTrace();
/* 110:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 111:    */     }
/* 112:    */     catch (IOException e)
/* 113:    */     {
/* 114:148 */       LOG.info("Error IOException");
/* 115:149 */       e.printStackTrace();
/* 116:150 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 117:    */     }
/* 118:152 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void cargarListaSubcategoriaProducto()
/* 122:    */   {
/* 123:156 */     HashMap<String, String> filters = new HashMap();
/* 124:157 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 125:158 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 129:    */   {
/* 130:162 */     HashMap<String, String> filters = new HashMap();
/* 131:163 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 132:164 */     if (this.listaCategoriaProductos == null) {
/* 133:165 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 134:    */     }
/* 135:167 */     return this.listaCategoriaProductos;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 139:    */   {
/* 140:171 */     return this.categoriaProductoSeleccionado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 144:    */   {
/* 145:175 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 149:    */   {
/* 150:179 */     return this.subcategoriaProductoSeleccionado;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 154:    */   {
/* 155:183 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 159:    */   {
/* 160:187 */     return this.listaSubcategoriaProductos;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 164:    */   {
/* 165:191 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 169:    */   {
/* 170:195 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public PlanProduccion getPlanProduccionSeleccionado()
/* 174:    */   {
/* 175:199 */     return this.planProduccionSeleccionado;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setPlanProduccionSeleccionado(PlanProduccion planProduccionSeleccionado)
/* 179:    */   {
/* 180:203 */     this.planProduccionSeleccionado = planProduccionSeleccionado;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List<PlanProduccion> getListaPlanProduccion()
/* 184:    */   {
/* 185:207 */     if (this.listaPlanProduccion == null)
/* 186:    */     {
/* 187:208 */       Map<String, String> filters = new HashMap();
/* 188:209 */       agregarFiltroOrganizacion(filters);
/* 189:210 */       this.listaPlanProduccion = this.servicioPlanProduccion.obtenerListaCombo("fechaInicio", false, filters);
/* 190:    */     }
/* 191:212 */     return this.listaPlanProduccion;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaPlanProduccion(List<PlanProduccion> listaPlanProduccion)
/* 195:    */   {
/* 196:216 */     this.listaPlanProduccion = listaPlanProduccion;
/* 197:    */   }
/* 198:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteMaterialesBean
 * JD-Core Version:    0.7.0.1
 */