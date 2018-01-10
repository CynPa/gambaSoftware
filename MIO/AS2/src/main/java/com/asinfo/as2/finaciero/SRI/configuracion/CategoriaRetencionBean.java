/*   1:    */ package com.asinfo.as2.finaciero.SRI.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaRetencion;
/*   6:    */ import com.asinfo.as2.entities.DetalleCategoriaRetencion;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*  10:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCategoriaRetencion;
/*  11:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import javax.faces.component.html.HtmlSelectOneMenu;
/*  22:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ import org.primefaces.model.LazyDataModel;
/*  26:    */ import org.primefaces.model.SortOrder;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class CategoriaRetencionBean
/*  31:    */   extends PageControllerAS2
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 4892824946188106627L;
/*  34:    */   @EJB
/*  35:    */   private transient ServicioCategoriaRetencion servicioCategoriaRetencion;
/*  36:    */   @EJB
/*  37:    */   private transient ServicioConceptoRetencionSRI servicioConceptoRetencionSRI;
/*  38:    */   private CategoriaRetencion categoriaRetencion;
/*  39:    */   private List<ConceptoRetencionSRI> listaConceptoRetencionSRI;
/*  40:    */   private LazyDataModel<CategoriaRetencion> listaCategoriaRetencion;
/*  41:    */   private DataTable dtCategoriaRetencion;
/*  42:    */   private DataTable dtDetalleCategoriaRetencion;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 80 */     this.listaCategoriaRetencion = new LazyDataModel()
/*  48:    */     {
/*  49:    */       private static final long serialVersionUID = 617376213460166729L;
/*  50:    */       
/*  51:    */       public List<CategoriaRetencion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */       {
/*  53: 87 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  54:    */         
/*  55: 89 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  56: 90 */         List<CategoriaRetencion> lista = CategoriaRetencionBean.this.servicioCategoriaRetencion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  57:    */         
/*  58:    */ 
/*  59: 93 */         CategoriaRetencionBean.this.listaCategoriaRetencion.setRowCount(CategoriaRetencionBean.this.servicioCategoriaRetencion.contarPorCriterio(filters));
/*  60: 94 */         return lista;
/*  61:    */       }
/*  62:    */     };
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void crearEntidad()
/*  66:    */   {
/*  67:108 */     this.categoriaRetencion = new CategoriaRetencion();
/*  68:109 */     this.categoriaRetencion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  69:110 */     this.categoriaRetencion.setIdSucursal(AppUtil.getSucursal().getId());
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void agregarDetalle()
/*  73:    */   {
/*  74:114 */     DetalleCategoriaRetencion detalleCategoriaRetencion = new DetalleCategoriaRetencion();
/*  75:115 */     detalleCategoriaRetencion.setCategoriaRetencion(this.categoriaRetencion);
/*  76:116 */     detalleCategoriaRetencion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  77:117 */     detalleCategoriaRetencion.setConceptoRetencionSRI(new ConceptoRetencionSRI());
/*  78:    */     
/*  79:119 */     this.categoriaRetencion.getListaDetalleCategoriaRetencion().add(detalleCategoriaRetencion);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String editar()
/*  83:    */   {
/*  84:128 */     if (getCategoriaRetencion().getIdCategoriaRetencion() > 0)
/*  85:    */     {
/*  86:129 */       setCategoriaRetencion(this.servicioCategoriaRetencion.cargarDetalle(getCategoriaRetencion().getId()));
/*  87:130 */       setEditado(true);
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:132 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  92:    */     }
/*  93:134 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String guardar()
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:144 */       this.servicioCategoriaRetencion.guardar(this.categoriaRetencion);
/* 101:145 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 102:146 */       setEditado(false);
/* 103:147 */       limpiar();
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 108:150 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 109:    */     }
/* 110:152 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String eliminar()
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:162 */       this.categoriaRetencion = this.servicioCategoriaRetencion.cargarDetalle(this.categoriaRetencion.getId());
/* 118:163 */       this.servicioCategoriaRetencion.eliminar(this.categoriaRetencion);
/* 119:164 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 120:    */     }
/* 121:    */     catch (Exception e)
/* 122:    */     {
/* 123:166 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 124:167 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 125:    */     }
/* 126:169 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String eliminarDetalle()
/* 130:    */   {
/* 131:173 */     DetalleCategoriaRetencion detalleCategoriaRetencion = (DetalleCategoriaRetencion)this.dtDetalleCategoriaRetencion.getRowData();
/* 132:174 */     detalleCategoriaRetencion.setEliminado(true);
/* 133:175 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void actualizarCategoriaRetencion(AjaxBehaviorEvent event)
/* 137:    */   {
/* 138:184 */     int idConceptoRetencion = Integer.parseInt(((HtmlSelectOneMenu)event.getSource()).getValue().toString());
/* 139:    */     
/* 140:186 */     DetalleCategoriaRetencion detalleCategoriaRetencion = (DetalleCategoriaRetencion)this.dtDetalleCategoriaRetencion.getRowData();
/* 141:187 */     ConceptoRetencionSRI conceptoRetencionSRI = this.servicioConceptoRetencionSRI.buscarPorId(Integer.valueOf(idConceptoRetencion));
/* 142:188 */     detalleCategoriaRetencion.setConceptoRetencionSRI(conceptoRetencionSRI);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String cargarDatos()
/* 146:    */   {
/* 147:197 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String limpiar()
/* 151:    */   {
/* 152:206 */     crearEntidad();
/* 153:207 */     return "";
/* 154:    */   }
/* 155:    */   
/* 156:    */   public CategoriaRetencion getCategoriaRetencion()
/* 157:    */   {
/* 158:223 */     return this.categoriaRetencion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setCategoriaRetencion(CategoriaRetencion categoriaRetencion)
/* 162:    */   {
/* 163:233 */     this.categoriaRetencion = categoriaRetencion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public LazyDataModel<CategoriaRetencion> getListaCategoriaRetencion()
/* 167:    */   {
/* 168:242 */     return this.listaCategoriaRetencion;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setListaCategoriaRetencion(LazyDataModel<CategoriaRetencion> listaCategoriaRetencion)
/* 172:    */   {
/* 173:252 */     this.listaCategoriaRetencion = listaCategoriaRetencion;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public DataTable getDtCategoriaRetencion()
/* 177:    */   {
/* 178:261 */     return this.dtCategoriaRetencion;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setDtCategoriaRetencion(DataTable dtCategoriaRetencion)
/* 182:    */   {
/* 183:271 */     this.dtCategoriaRetencion = dtCategoriaRetencion;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<DetalleCategoriaRetencion> getListaDetalleCategoriaRetencion()
/* 187:    */   {
/* 188:280 */     List<DetalleCategoriaRetencion> lista = new ArrayList();
/* 189:281 */     for (DetalleCategoriaRetencion detalleCategoriaRetencion : getCategoriaRetencion().getListaDetalleCategoriaRetencion()) {
/* 190:282 */       if (!detalleCategoriaRetencion.isEliminado()) {
/* 191:283 */         lista.add(detalleCategoriaRetencion);
/* 192:    */       }
/* 193:    */     }
/* 194:287 */     return lista;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public DataTable getDtDetalleCategoriaRetencion()
/* 198:    */   {
/* 199:296 */     return this.dtDetalleCategoriaRetencion;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setDtDetalleCategoriaRetencion(DataTable dtDetalleCategoriaRetencion)
/* 203:    */   {
/* 204:306 */     this.dtDetalleCategoriaRetencion = dtDetalleCategoriaRetencion;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public List<ConceptoRetencionSRI> getListaConceptoRetencionSRI()
/* 208:    */   {
/* 209:315 */     if (this.listaConceptoRetencionSRI == null)
/* 210:    */     {
/* 211:316 */       HashMap<String, String> filters = new HashMap();
/* 212:317 */       filters.put("activo", "true");
/* 213:318 */       this.listaConceptoRetencionSRI = this.servicioConceptoRetencionSRI.obtenerListaCombo("codigo", true, filters);
/* 214:    */     }
/* 215:320 */     return this.listaConceptoRetencionSRI;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setListaConceptoRetencionSRI(List<ConceptoRetencionSRI> listaConceptoRetencionSRI)
/* 219:    */   {
/* 220:330 */     this.listaConceptoRetencionSRI = listaConceptoRetencionSRI;
/* 221:    */   }
/* 222:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.configuracion.CategoriaRetencionBean
 * JD-Core Version:    0.7.0.1
 */