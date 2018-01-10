/*   1:    */ package com.asinfo.as2.produccion.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCentroTrabajo;
/*   6:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*  10:    */ import com.asinfo.as2.entities.produccion.TareaProduccion;
/*  11:    */ import com.asinfo.as2.entities.produccion.TarifaOperacion;
/*  12:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMaquina;
/*  13:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioTareaProduccion;
/*  14:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioTarifaOperacion;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ import org.primefaces.model.LazyDataModel;
/*  26:    */ import org.primefaces.model.SortOrder;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class TareaProduccionBean
/*  31:    */   extends PageControllerAS2
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  34:    */   @EJB
/*  35:    */   private ServicioTareaProduccion servicioTareaProduccion;
/*  36:    */   @EJB
/*  37:    */   private ServicioMaquina servicioMaquina;
/*  38:    */   @EJB
/*  39:    */   private ServicioCentroTrabajo servicioCentroTrabajo;
/*  40:    */   @EJB
/*  41:    */   private ServicioTarifaOperacion servicioTarifaOperacion;
/*  42:    */   private TareaProduccion tareaProduccion;
/*  43:    */   private LazyDataModel<TareaProduccion> listaTareaProduccion;
/*  44:    */   private List<CentroTrabajo> listaCentroTrabajo;
/*  45:    */   private List<Maquina> listaMaquina;
/*  46:    */   private List<TarifaOperacion> listaTarifaOperacion;
/*  47:    */   private DataTable dtTareaProduccion;
/*  48:    */   
/*  49:    */   @PostConstruct
/*  50:    */   public void init()
/*  51:    */   {
/*  52: 91 */     this.listaTareaProduccion = new LazyDataModel()
/*  53:    */     {
/*  54:    */       private static final long serialVersionUID = 1L;
/*  55:    */       
/*  56:    */       public List<TareaProduccion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  57:    */       {
/*  58: 98 */         List<TareaProduccion> lista = new ArrayList();
/*  59: 99 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  60:    */         
/*  61:101 */         lista = TareaProduccionBean.this.servicioTareaProduccion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  62:    */         
/*  63:103 */         TareaProduccionBean.this.listaTareaProduccion.setRowCount(TareaProduccionBean.this.servicioTareaProduccion.contarPorCriterio(filters));
/*  64:    */         
/*  65:105 */         return lista;
/*  66:    */       }
/*  67:    */     };
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void crearEntidad()
/*  71:    */   {
/*  72:123 */     this.tareaProduccion = new TareaProduccion();
/*  73:124 */     this.tareaProduccion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  74:125 */     this.tareaProduccion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  75:126 */     this.tareaProduccion.setActivo(true);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String editar()
/*  79:    */   {
/*  80:135 */     if (getTareaProduccion().getIdTareaProduccion() > 0)
/*  81:    */     {
/*  82:136 */       this.tareaProduccion = this.servicioTareaProduccion.cargarDetalle(this.tareaProduccion.getIdTareaProduccion());
/*  83:137 */       setEditado(true);
/*  84:    */     }
/*  85:    */     else
/*  86:    */     {
/*  87:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  88:    */     }
/*  89:141 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String guardar()
/*  93:    */   {
/*  94:    */     try
/*  95:    */     {
/*  96:151 */       this.servicioTareaProduccion.guardar(this.tareaProduccion);
/*  97:152 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  98:153 */       limpiar();
/*  99:154 */       setEditado(false);
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:156 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 104:157 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 105:    */     }
/* 106:159 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String eliminar()
/* 110:    */   {
/* 111:    */     try
/* 112:    */     {
/* 113:169 */       this.servicioTareaProduccion.eliminar(this.tareaProduccion);
/* 114:170 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:172 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 119:173 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 120:    */     }
/* 121:175 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String cargarDatos()
/* 125:    */   {
/* 126:184 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String limpiar()
/* 130:    */   {
/* 131:193 */     crearEntidad();
/* 132:194 */     return "";
/* 133:    */   }
/* 134:    */   
/* 135:    */   public TareaProduccion getTareaProduccion()
/* 136:    */   {
/* 137:207 */     return this.tareaProduccion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setTareaProduccion(TareaProduccion tareaProduccion)
/* 141:    */   {
/* 142:217 */     this.tareaProduccion = tareaProduccion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public LazyDataModel<TareaProduccion> getListaTareaProduccion()
/* 146:    */   {
/* 147:226 */     return this.listaTareaProduccion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setListaTareaProduccion(LazyDataModel<TareaProduccion> listaTareaProduccion)
/* 151:    */   {
/* 152:236 */     this.listaTareaProduccion = listaTareaProduccion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public DataTable getDtTareaProduccion()
/* 156:    */   {
/* 157:245 */     return this.dtTareaProduccion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setDtTareaProduccion(DataTable dtTareaProduccion)
/* 161:    */   {
/* 162:255 */     this.dtTareaProduccion = dtTareaProduccion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<CentroTrabajo> getListaCentroTrabajo()
/* 166:    */   {
/* 167:264 */     if (this.listaCentroTrabajo == null) {
/* 168:265 */       this.listaCentroTrabajo = this.servicioCentroTrabajo.obtenerListaCombo("nombre", true, null);
/* 169:    */     }
/* 170:267 */     return this.listaCentroTrabajo;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public List<Maquina> getListaMaquina()
/* 174:    */   {
/* 175:276 */     if (this.listaMaquina == null) {
/* 176:277 */       this.listaMaquina = this.servicioMaquina.obtenerListaCombo("nombre", true, null);
/* 177:    */     }
/* 178:279 */     return this.listaMaquina;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<TarifaOperacion> getListaTarifaOperacion()
/* 182:    */   {
/* 183:288 */     if (this.listaTarifaOperacion == null) {
/* 184:289 */       this.listaTarifaOperacion = this.servicioTarifaOperacion.obtenerListaCombo("nombre", true, null);
/* 185:    */     }
/* 186:291 */     return this.listaTarifaOperacion;
/* 187:    */   }
/* 188:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.controller.TareaProduccionBean
 * JD-Core Version:    0.7.0.1
 */