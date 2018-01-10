/*   1:    */ package com.asinfo.as2.finaciero.SRI.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   8:    */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*   9:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  10:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ManagedProperty;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import javax.faces.model.SelectItem;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class ConceptoRetencionBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -8069520209051473664L;
/*  32:    */   @EJB
/*  33:    */   private transient ServicioConceptoRetencionSRI servicioConceptoRetencionSRI;
/*  34:    */   private ConceptoRetencionSRI conceptoRetencionSRI;
/*  35:    */   private LazyDataModel<ConceptoRetencionSRI> listaConceptoRetencionSRI;
/*  36:    */   private List<ConceptoRetencionSRI> listaConceptoRetencionSRICombo;
/*  37:    */   private List<SelectItem> listaTipoConceptoRetencion;
/*  38:    */   private CuentaContable cuentaContable;
/*  39:    */   private DataTable dtConceptoRetencionSRI;
/*  40:    */   private DataTable dtCuentaContable;
/*  41:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  42:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 72 */     this.listaConceptoRetencionSRI = new LazyDataModel()
/*  48:    */     {
/*  49:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  50:    */       
/*  51:    */       public List<ConceptoRetencionSRI> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */       {
/*  53: 79 */         List<ConceptoRetencionSRI> lista = new ArrayList();
/*  54:    */         
/*  55: 81 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  56: 82 */         if (filters.size() == 0) {
/*  57: 83 */           filters.put("activo", "true");
/*  58:    */         }
/*  59:    */         try
/*  60:    */         {
/*  61: 86 */           lista = ConceptoRetencionBean.this.servicioConceptoRetencionSRI.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  62:    */         }
/*  63:    */         catch (Exception e)
/*  64:    */         {
/*  65: 89 */           ConceptoRetencionBean.LOG.error("Error al cargar lista de ConceptoRetencionSRI " + e);
/*  66:    */         }
/*  67: 91 */         ConceptoRetencionBean.this.listaConceptoRetencionSRI.setRowCount(ConceptoRetencionBean.this.servicioConceptoRetencionSRI.contarPorCriterio(filters));
/*  68: 92 */         return lista;
/*  69:    */       }
/*  70:    */     };
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void cargarCuentaContable()
/*  74:    */   {
/*  75:102 */     this.cuentaContable = ((CuentaContable)this.dtCuentaContable.getRowData());
/*  76:103 */     getConceptoRetencionSRI().setCuentaContable(this.cuentaContable);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void actualizarCuentaContable()
/*  80:    */   {
/*  81:107 */     this.cuentaContable = getConceptoRetencionSRI().getCuentaContable();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String crear()
/*  85:    */   {
/*  86:117 */     limpiar();
/*  87:    */     
/*  88:119 */     this.conceptoRetencionSRI = new ConceptoRetencionSRI();
/*  89:120 */     this.conceptoRetencionSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  90:    */     
/*  91:122 */     setEditado(true);
/*  92:123 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String editar()
/*  96:    */   {
/*  97:133 */     if (getConceptoRetencionSRI().getId() > 0) {
/*  98:134 */       setEditado(true);
/*  99:    */     } else {
/* 100:136 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 101:    */     }
/* 102:138 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String guardar()
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109:150 */       this.servicioConceptoRetencionSRI.guardar(this.conceptoRetencionSRI);
/* 110:    */       
/* 111:152 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 112:    */       
/* 113:154 */       setEditado(false);
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 118:158 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 119:    */     }
/* 120:160 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String eliminar()
/* 124:    */   {
/* 125:170 */     this.servicioConceptoRetencionSRI.eliminar(this.conceptoRetencionSRI);
/* 126:    */     
/* 127:172 */     addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 128:    */     
/* 129:174 */     cargarDatos();
/* 130:175 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String limpiar()
/* 134:    */   {
/* 135:185 */     this.conceptoRetencionSRI = new ConceptoRetencionSRI();
/* 136:186 */     setEditado(false);
/* 137:    */     
/* 138:188 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String cargarDatos()
/* 142:    */   {
/* 143:198 */     return null;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public ConceptoRetencionSRI getConceptoRetencionSRI()
/* 147:    */   {
/* 148:209 */     if (this.conceptoRetencionSRI == null) {
/* 149:210 */       limpiar();
/* 150:    */     }
/* 151:212 */     return this.conceptoRetencionSRI;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setConceptoRetencionSRI(ConceptoRetencionSRI conceptoRetencionSRI)
/* 155:    */   {
/* 156:222 */     this.conceptoRetencionSRI = conceptoRetencionSRI;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public LazyDataModel<ConceptoRetencionSRI> getListaConceptoRetencionSRI()
/* 160:    */   {
/* 161:231 */     if (this.listaConceptoRetencionSRI == null) {
/* 162:232 */       cargarDatos();
/* 163:    */     }
/* 164:234 */     return this.listaConceptoRetencionSRI;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setListaConceptoRetencionSRI(LazyDataModel<ConceptoRetencionSRI> listaConceptoRetencionSRI)
/* 168:    */   {
/* 169:244 */     this.listaConceptoRetencionSRI = listaConceptoRetencionSRI;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public DataTable getDtConceptoRetencionSRI()
/* 173:    */   {
/* 174:253 */     return this.dtConceptoRetencionSRI;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setDtConceptoRetencionSRI(DataTable dtConceptoRetencionSRI)
/* 178:    */   {
/* 179:263 */     this.dtConceptoRetencionSRI = dtConceptoRetencionSRI;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List<SelectItem> getListaTipoConceptoRetencion()
/* 183:    */   {
/* 184:272 */     if (this.listaTipoConceptoRetencion == null)
/* 185:    */     {
/* 186:273 */       this.listaTipoConceptoRetencion = new ArrayList();
/* 187:274 */       for (TipoConceptoRetencion t : TipoConceptoRetencion.values())
/* 188:    */       {
/* 189:275 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 190:276 */         this.listaTipoConceptoRetencion.add(item);
/* 191:    */       }
/* 192:    */     }
/* 193:279 */     return this.listaTipoConceptoRetencion;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setListaTipoConceptoRetencion(List<SelectItem> listaTipoConceptoRetencion)
/* 197:    */   {
/* 198:289 */     this.listaTipoConceptoRetencion = listaTipoConceptoRetencion;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public CuentaContable getCuentaContable()
/* 202:    */   {
/* 203:298 */     return this.cuentaContable;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 207:    */   {
/* 208:308 */     this.cuentaContable = cuentaContable;
/* 209:309 */     getConceptoRetencionSRI().setCuentaContable(cuentaContable);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public DataTable getDtCuentaContable()
/* 213:    */   {
/* 214:313 */     return this.dtCuentaContable;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 218:    */   {
/* 219:317 */     this.dtCuentaContable = dtCuentaContable;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public List<ConceptoRetencionSRI> getListaConceptoRetencionSRICombo()
/* 223:    */   {
/* 224:326 */     if (this.listaConceptoRetencionSRICombo == null) {
/* 225:327 */       this.listaConceptoRetencionSRICombo = this.servicioConceptoRetencionSRI.obtenerListaCombo("nombre", true, null);
/* 226:    */     }
/* 227:329 */     return this.listaConceptoRetencionSRICombo;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 231:    */   {
/* 232:333 */     return this.listaCuentaContableBean;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 236:    */   {
/* 237:337 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.configuracion.ConceptoRetencionBean
 * JD-Core Version:    0.7.0.1
 */