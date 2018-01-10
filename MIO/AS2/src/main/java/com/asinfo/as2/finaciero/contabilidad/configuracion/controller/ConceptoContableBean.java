/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.ConceptoContable;
/*   6:    */ import com.asinfo.as2.entities.CuentaContable;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioConceptoContable;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ManagedProperty;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ import org.primefaces.component.datatable.DataTable;
/*  24:    */ import org.primefaces.event.SelectEvent;
/*  25:    */ import org.primefaces.model.LazyDataModel;
/*  26:    */ import org.primefaces.model.SortOrder;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class ConceptoContableBean
/*  31:    */   extends PageControllerAS2
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = -8036057709751358020L;
/*  34:    */   @EJB
/*  35:    */   ServicioConceptoContable servicioConceptoContable;
/*  36:    */   @EJB
/*  37:    */   ServicioCuentaContable servicioCuentaContable;
/*  38:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  39:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  40:    */   private ConceptoContable conceptoContable;
/*  41:    */   private LazyDataModel<ConceptoContable> listaConceptoContable;
/*  42:    */   private CuentaContable cuentaContable;
/*  43:    */   private enumCuentaContableEditada cuentaContableEditada;
/*  44:    */   private DataTable dtConceptoContable;
/*  45:    */   private DataTable dtCuentaContable;
/*  46:    */   
/*  47:    */   private static enum enumCuentaContableEditada
/*  48:    */   {
/*  49: 60 */     CUENTA_CONTABLE_DEBE,  CUENTA_CONTABLE_HABER;
/*  50:    */     
/*  51:    */     private enumCuentaContableEditada() {}
/*  52:    */   }
/*  53:    */   
/*  54:    */   @PostConstruct
/*  55:    */   public void init()
/*  56:    */   {
/*  57: 70 */     this.listaConceptoContable = new LazyDataModel()
/*  58:    */     {
/*  59:    */       private static final long serialVersionUID = 1L;
/*  60:    */       
/*  61:    */       public List<ConceptoContable> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  62:    */       {
/*  63: 76 */         List<ConceptoContable> lista = new ArrayList();
/*  64:    */         
/*  65: 78 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  66: 80 */         if (sortField == null)
/*  67:    */         {
/*  68: 81 */           sortField = "codigo";
/*  69: 82 */           ordenar = true;
/*  70:    */         }
/*  71: 85 */         lista = ConceptoContableBean.this.servicioConceptoContable.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  72:    */         
/*  73: 87 */         ConceptoContableBean.this.listaConceptoContable.setRowCount(ConceptoContableBean.this.servicioConceptoContable.contarPorCriterio(filters));
/*  74:    */         
/*  75: 89 */         return lista;
/*  76:    */       }
/*  77:    */     };
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String editar()
/*  81:    */   {
/*  82: 96 */     if (getConceptoContable().getId() > 0) {
/*  83: 97 */       setEditado(true);
/*  84:    */     } else {
/*  85: 99 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  86:    */     }
/*  87:101 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String guardar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:108 */       this.servicioConceptoContable.guardar(this.conceptoContable);
/*  95:    */       
/*  96:110 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  97:    */       
/*  98:112 */       limpiar();
/*  99:113 */       setEditado(false);
/* 100:    */     }
/* 101:    */     catch (ExcepcionAS2 e)
/* 102:    */     {
/* 103:115 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 104:116 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:118 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 109:119 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 110:    */     }
/* 111:121 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String eliminar()
/* 115:    */   {
/* 116:    */     try
/* 117:    */     {
/* 118:127 */       this.servicioConceptoContable.eliminar(this.conceptoContable);
/* 119:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 120:    */     }
/* 121:    */     catch (Exception e)
/* 122:    */     {
/* 123:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 124:131 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 125:    */     }
/* 126:133 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String limpiar()
/* 130:    */   {
/* 131:138 */     this.conceptoContable = new ConceptoContable();
/* 132:139 */     this.conceptoContable.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 133:140 */     this.conceptoContable.setIdSucursal(AppUtil.getSucursal().getId());
/* 134:141 */     this.conceptoContable.setCuentaContableDebe(new CuentaContable());
/* 135:142 */     this.conceptoContable.setCuentaContableHaber(new CuentaContable());
/* 136:143 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String cargarDatos()
/* 140:    */   {
/* 141:148 */     return "";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<ConceptoContable> autocompletarConceptoContable(String consulta)
/* 145:    */   {
/* 146:152 */     List<ConceptoContable> lista = new ArrayList();
/* 147:    */     
/* 148:154 */     String sortField = "codigo";
/* 149:155 */     HashMap<String, String> filters = new HashMap();
/* 150:156 */     filters.put("nombre", consulta.trim() + "%");
/* 151:157 */     lista = this.servicioConceptoContable.obtenerListaCombo(sortField, true, filters);
/* 152:158 */     return lista;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void seleccionarCuentaContable(SelectEvent event)
/* 156:    */   {
/* 157:163 */     this.cuentaContable = ((CuentaContable)event.getObject());
/* 158:164 */     switch (2.$SwitchMap$com$asinfo$as2$finaciero$contabilidad$configuracion$controller$ConceptoContableBean$enumCuentaContableEditada[this.cuentaContableEditada.ordinal()])
/* 159:    */     {
/* 160:    */     case 1: 
/* 161:167 */       this.conceptoContable.setCuentaContableDebe(this.cuentaContable);
/* 162:168 */       break;
/* 163:    */     case 2: 
/* 164:171 */       this.conceptoContable.setCuentaContableHaber(this.cuentaContable);
/* 165:172 */       break;
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void actualizarCuentaContableDebe()
/* 170:    */   {
/* 171:180 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_DEBE;
/* 172:181 */     this.cuentaContable = this.conceptoContable.getCuentaContableDebe();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void actualizarCuentaContableHaber()
/* 176:    */   {
/* 177:185 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_HABER;
/* 178:186 */     this.cuentaContable = this.conceptoContable.getCuentaContableHaber();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public ConceptoContable getConceptoContable()
/* 182:    */   {
/* 183:190 */     if (this.conceptoContable == null) {
/* 184:191 */       limpiar();
/* 185:    */     }
/* 186:193 */     return this.conceptoContable;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setConceptoContable(ConceptoContable conceptoContable)
/* 190:    */   {
/* 191:197 */     this.conceptoContable = conceptoContable;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public LazyDataModel<ConceptoContable> getListaConceptoContable()
/* 195:    */   {
/* 196:201 */     return this.listaConceptoContable;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setListaConceptoContable(LazyDataModel<ConceptoContable> listaConceptoContable)
/* 200:    */   {
/* 201:205 */     this.listaConceptoContable = listaConceptoContable;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public CuentaContable getCuentaContable()
/* 205:    */   {
/* 206:209 */     return this.cuentaContable;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 210:    */   {
/* 211:213 */     this.cuentaContable = cuentaContable;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public enumCuentaContableEditada getCuentaContableEditada()
/* 215:    */   {
/* 216:217 */     return this.cuentaContableEditada;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setCuentaContableEditada(enumCuentaContableEditada cuentaContableEditada)
/* 220:    */   {
/* 221:221 */     this.cuentaContableEditada = cuentaContableEditada;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public DataTable getDtConceptoContable()
/* 225:    */   {
/* 226:225 */     return this.dtConceptoContable;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setDtConceptoContable(DataTable dtConceptoContable)
/* 230:    */   {
/* 231:229 */     this.dtConceptoContable = dtConceptoContable;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public DataTable getDtCuentaContable()
/* 235:    */   {
/* 236:233 */     return this.dtCuentaContable;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 240:    */   {
/* 241:237 */     this.dtCuentaContable = dtCuentaContable;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 245:    */   {
/* 246:241 */     return this.listaCuentaContableBean;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 250:    */   {
/* 251:246 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 252:    */   }
/* 253:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ConceptoContableBean
 * JD-Core Version:    0.7.0.1
 */