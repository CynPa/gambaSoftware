/*   1:    */ package com.asinfo.as2.seguridad.configuracion.cotroller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Modulo;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*   9:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  10:    */ import com.asinfo.as2.seguridad.ServicioModulo;
/*  11:    */ import com.asinfo.as2.seguridad.ServicioProceso;
/*  12:    */ import com.asinfo.as2.seguridad.ServicioSistema;
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
/*  28:    */ public class ProcesoBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @EJB
/*  33:    */   private ServicioProceso servicioProceso;
/*  34:    */   @EJB
/*  35:    */   private ServicioSistema servicioSistema;
/*  36:    */   @EJB
/*  37:    */   private ServicioModulo servicioModulo;
/*  38:    */   private EntidadProceso entidadProceso;
/*  39:    */   private LazyDataModel<EntidadProceso> listaEntidadProceso;
/*  40:    */   private List<EntidadSistema> listaSistema;
/*  41:    */   private List<Modulo> listaModulo;
/*  42:    */   private DataTable dtEntidadProceso;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49: 71 */       this.listaEntidadProceso = new LazyDataModel()
/*  50:    */       {
/*  51:    */         private static final long serialVersionUID = 4528509999642173756L;
/*  52:    */         
/*  53:    */         public List<EntidadProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  54:    */         {
/*  55: 84 */           List<EntidadProceso> lista = new ArrayList();
/*  56: 85 */           filters.put("activo", "true");
/*  57: 86 */           filters.put("idOrganizacion", "!=-1");
/*  58:    */           
/*  59: 88 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  60: 89 */           lista = ProcesoBean.this.servicioProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  61: 90 */           ProcesoBean.this.listaEntidadProceso.setRowCount(ProcesoBean.this.servicioProceso.contarPorCriterio(filters));
/*  62: 91 */           return lista;
/*  63:    */         }
/*  64:    */       };
/*  65:    */     }
/*  66:    */     catch (Exception e)
/*  67:    */     {
/*  68: 96 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  69: 97 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String editar()
/*  74:    */   {
/*  75:108 */     if (this.entidadProceso.getId() > 0) {
/*  76:109 */       setEditado(true);
/*  77:    */     } else {
/*  78:111 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  79:    */     }
/*  80:113 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String guardar()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:125 */       this.servicioProceso.guardar(this.entidadProceso);
/*  88:126 */       cargarDatos();
/*  89:127 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  90:    */     }
/*  91:    */     catch (Exception e)
/*  92:    */     {
/*  93:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  94:130 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  95:    */     }
/*  96:132 */     return "";
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String eliminar()
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:143 */       this.servicioProceso.eliminar(this.entidadProceso);
/* 104:144 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 109:147 */       LOG.error("ERROR AL ELMINAR DATOS", e);
/* 110:    */     }
/* 111:149 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String limpiar()
/* 115:    */   {
/* 116:159 */     this.entidadProceso = new EntidadProceso();
/* 117:    */     
/* 118:161 */     this.entidadProceso.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 119:162 */     this.entidadProceso.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 120:163 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String cargarDatos()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:174 */       setEditado(false);
/* 128:    */       
/* 129:176 */       limpiar();
/* 130:    */     }
/* 131:    */     catch (Exception e)
/* 132:    */     {
/* 133:178 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 134:179 */       LOG.error("ERROR AL CARGAR LOS DATOS", e);
/* 135:    */     }
/* 136:181 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public EntidadProceso getEntidadProceso()
/* 140:    */   {
/* 141:192 */     if (this.entidadProceso == null) {
/* 142:193 */       this.entidadProceso = new EntidadProceso();
/* 143:    */     }
/* 144:195 */     return this.entidadProceso;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setEntidadProceso(EntidadProceso entidadProceso)
/* 148:    */   {
/* 149:205 */     this.entidadProceso = entidadProceso;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public LazyDataModel<EntidadProceso> getListaEntidadProceso()
/* 153:    */   {
/* 154:214 */     if (this.listaEntidadProceso == null) {
/* 155:215 */       cargarDatos();
/* 156:    */     }
/* 157:217 */     return this.listaEntidadProceso;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaEntidadProceso(LazyDataModel<EntidadProceso> listaEntidadProceso)
/* 161:    */   {
/* 162:227 */     this.listaEntidadProceso = listaEntidadProceso;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public DataTable getDtEntidadProceso()
/* 166:    */   {
/* 167:236 */     return this.dtEntidadProceso;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setDtEntidadProceso(DataTable dtEntidadProceso)
/* 171:    */   {
/* 172:246 */     this.dtEntidadProceso = dtEntidadProceso;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<EntidadSistema> getListaSistema()
/* 176:    */   {
/* 177:255 */     if (this.listaSistema == null) {
/* 178:256 */       this.listaSistema = this.servicioSistema.obtenerListaCombo("nombre", true, null);
/* 179:    */     }
/* 180:258 */     return this.listaSistema;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setListaSistema(List<EntidadSistema> listaSistema)
/* 184:    */   {
/* 185:268 */     this.listaSistema = listaSistema;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<Modulo> getListaModulo()
/* 189:    */   {
/* 190:272 */     if (this.listaModulo == null) {
/* 191:273 */       this.listaModulo = this.servicioModulo.obtenerListaCombo("nombre", true, null);
/* 192:    */     }
/* 193:275 */     return this.listaModulo;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setListaModulo(List<Modulo> listaModulo)
/* 197:    */   {
/* 198:279 */     this.listaModulo = listaModulo;
/* 199:    */   }
/* 200:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.configuracion.cotroller.ProcesoBean
 * JD-Core Version:    0.7.0.1
 */