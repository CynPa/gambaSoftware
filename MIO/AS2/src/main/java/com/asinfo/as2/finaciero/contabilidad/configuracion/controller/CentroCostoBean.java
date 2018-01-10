/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CentroCosto;
/*   6:    */ import com.asinfo.as2.entities.NivelCentroCosto;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCentroCosto;
/*  12:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class CentroCostoBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 922450216445924585L;
/*  32:    */   @EJB
/*  33:    */   private ServicioCentroCosto servicioCentroCosto;
/*  34:    */   @EJB
/*  35:    */   private ServicioNivelCentroCosto servicioNivelCentroCosto;
/*  36:    */   private CentroCosto centroCosto;
/*  37:    */   private LazyDataModel<CentroCosto> listaCentroCosto;
/*  38:    */   private DataTable dtCentroCosto;
/*  39:    */   private List<NivelCentroCosto> listaNivelCentroCosto;
/*  40:    */   private List<CentroCosto> listaCentroCostoPadre;
/*  41:    */   public List<CentroCosto> listaCentroCostoCombo;
/*  42:    */   
/*  43:    */   @PostConstruct
/*  44:    */   public void init()
/*  45:    */   {
/*  46: 80 */     limpiar();
/*  47:    */     
/*  48: 82 */     this.listaCentroCosto = new LazyDataModel()
/*  49:    */     {
/*  50:    */       private static final long serialVersionUID = -7151429180238501121L;
/*  51:    */       
/*  52:    */       public List<CentroCosto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  53:    */       {
/*  54: 88 */         List<CentroCosto> lista = new ArrayList();
/*  55: 89 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  56:    */         try
/*  57:    */         {
/*  58: 91 */           lista = CentroCostoBean.this.servicioCentroCosto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  59: 92 */           CentroCostoBean.this.listaCentroCosto.setRowCount(CentroCostoBean.this.servicioCentroCosto.contarPorCriterio(filters));
/*  60:    */         }
/*  61:    */         catch (ExcepcionAS2Financiero e)
/*  62:    */         {
/*  63: 95 */           CentroCostoBean.LOG.info(e.getErrorMessage(e));
/*  64:    */         }
/*  65: 97 */         return lista;
/*  66:    */       }
/*  67:    */     };
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String editar()
/*  71:    */   {
/*  72:111 */     if ((getCentroCosto() != null) || (getCentroCosto().getId() > 0))
/*  73:    */     {
/*  74:112 */       setCentroCosto(this.servicioCentroCosto.cargarDetalle(getCentroCosto().getId()));
/*  75:    */       
/*  76:114 */       cargarCentroCostoPadre();
/*  77:    */       
/*  78:116 */       setEditado(true);
/*  79:    */     }
/*  80:    */     else
/*  81:    */     {
/*  82:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  83:    */     }
/*  84:120 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String guardar()
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:131 */       this.servicioCentroCosto.guardar(this.centroCosto);
/*  92:132 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  93:133 */       limpiar();
/*  94:    */     }
/*  95:    */     catch (ExcepcionAS2Financiero e)
/*  96:    */     {
/*  97:136 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  98:137 */       LOG.info("ERROR AL GUARDAR DATOS CENTROS DE COSTOS", e);
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:140 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 103:141 */       LOG.error("ERROR AL GUARDAR DATOS CENTROS DE COSTOS", e);
/* 104:    */     }
/* 105:143 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String eliminar()
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:154 */       this.servicioCentroCosto.eliminar(this.centroCosto);
/* 113:155 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 118:158 */       LOG.error("ERROR AL ELMINAR DATOS", e);
/* 119:    */     }
/* 120:160 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String limpiar()
/* 124:    */   {
/* 125:170 */     setEditado(false);
/* 126:    */     
/* 127:172 */     this.centroCosto = new CentroCosto();
/* 128:173 */     this.centroCosto.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 129:174 */     this.centroCosto.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 130:    */     
/* 131:176 */     this.listaCentroCostoPadre = new ArrayList();
/* 132:    */     
/* 133:178 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String cargarDatos()
/* 137:    */   {
/* 138:188 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void cargarCentroCostoPadre()
/* 142:    */   {
/* 143:196 */     this.listaCentroCostoPadre.clear();
/* 144:    */     
/* 145:198 */     int codigoNivelCentroCosto = this.centroCosto.getNivelCentroCosto().getCodigo() - 1;
/* 146:200 */     if (codigoNivelCentroCosto > 0) {
/* 147:    */       try
/* 148:    */       {
/* 149:202 */         this.listaCentroCostoPadre = this.servicioCentroCosto.buscarPorCodigoNivelCentroCosto(codigoNivelCentroCosto);
/* 150:    */       }
/* 151:    */       catch (ExcepcionAS2 e)
/* 152:    */       {
/* 153:204 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 154:    */       }
/* 155:    */     }
/* 156:208 */     obtenerMascara();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String obtenerMascara()
/* 160:    */   {
/* 161:217 */     String mascara = this.servicioNivelCentroCosto.getMascara(this.centroCosto);
/* 162:    */     
/* 163:219 */     this.centroCosto.setTraMascara(mascara);
/* 164:    */     
/* 165:221 */     return "";
/* 166:    */   }
/* 167:    */   
/* 168:    */   public CentroCosto getCentroCosto()
/* 169:    */   {
/* 170:232 */     return this.centroCosto;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setCentroCosto(CentroCosto centroCosto)
/* 174:    */   {
/* 175:242 */     this.centroCosto = centroCosto;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public LazyDataModel<CentroCosto> getListaCentroCosto()
/* 179:    */   {
/* 180:251 */     return this.listaCentroCosto;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setListaCentroCosto(LazyDataModel<CentroCosto> listaCentroCosto)
/* 184:    */   {
/* 185:261 */     this.listaCentroCosto = listaCentroCosto;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public DataTable getDtCentroCosto()
/* 189:    */   {
/* 190:270 */     return this.dtCentroCosto;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setDtCentroCosto(DataTable dtCentroCosto)
/* 194:    */   {
/* 195:280 */     this.dtCentroCosto = dtCentroCosto;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<NivelCentroCosto> getListaNivelCentroCosto()
/* 199:    */   {
/* 200:289 */     if (this.listaNivelCentroCosto == null) {
/* 201:290 */       this.listaNivelCentroCosto = this.servicioNivelCentroCosto.obtenerListaCombo("codigo", true, null);
/* 202:    */     }
/* 203:292 */     return this.listaNivelCentroCosto;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setListaNivelCentroCosto(List<NivelCentroCosto> listaNivelCentroCosto)
/* 207:    */   {
/* 208:302 */     this.listaNivelCentroCosto = listaNivelCentroCosto;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public List<CentroCosto> getListaCentroCostoPadre()
/* 212:    */   {
/* 213:311 */     return this.listaCentroCostoPadre;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setListaCentroCostoPadre(List<CentroCosto> listaCentroCostoPadre)
/* 217:    */   {
/* 218:321 */     this.listaCentroCostoPadre = listaCentroCostoPadre;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<CentroCosto> getListaCentroCostoCombo()
/* 222:    */   {
/* 223:325 */     this.listaCentroCostoCombo = obtenerListaCombo();
/* 224:326 */     return this.listaCentroCostoCombo;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setListaCentroCostoCombo(List<CentroCosto> listaCentroCostoCombo)
/* 228:    */   {
/* 229:330 */     this.listaCentroCostoCombo = listaCentroCostoCombo;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public List<CentroCosto> obtenerListaCombo()
/* 233:    */   {
/* 234:334 */     List<CentroCosto> lista = new ArrayList();
/* 235:335 */     String sortField = "codigo";
/* 236:336 */     lista = this.servicioCentroCosto.obtenerListaCombo(sortField, true, null);
/* 237:337 */     return lista;
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.CentroCostoBean
 * JD-Core Version:    0.7.0.1
 */