/*   1:    */ package com.asinfo.as2.polizas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   9:    */ import com.asinfo.as2.entities.polizas.Broker;
/*  10:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioBroker;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class BrokerBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioBroker servicioBroker;
/*  32:    */   @EJB
/*  33:    */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  34:    */   private Broker broker;
/*  35:    */   private LazyDataModel<Broker> listaBroker;
/*  36:    */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*  37:    */   private DataTable dtBroker;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 76 */     this.listaBroker = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = 1L;
/*  45:    */       
/*  46:    */       public List<Broker> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 83 */         List<Broker> lista = new ArrayList();
/*  49: 84 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 86 */         lista = BrokerBean.this.servicioBroker.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  52:    */         
/*  53: 88 */         BrokerBean.this.listaBroker.setRowCount(BrokerBean.this.servicioBroker.contarPorCriterio(filters));
/*  54: 89 */         return lista;
/*  55:    */       }
/*  56:    */     };
/*  57:    */   }
/*  58:    */   
/*  59:    */   private void crearBroker()
/*  60:    */   {
/*  61:103 */     this.broker = new Broker();
/*  62:104 */     this.broker.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  63:105 */     this.broker.setIdSucursal(AppUtil.getSucursal().getId());
/*  64:106 */     this.broker.setTipoIdentificacion(new TipoIdentificacion());
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String editar()
/*  68:    */   {
/*  69:115 */     if (getBroker().getIdBroker() > 0) {
/*  70:116 */       setEditado(true);
/*  71:    */     } else {
/*  72:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  73:    */     }
/*  74:120 */     return "";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String guardar()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:130 */       this.servicioBroker.guardar(getBroker());
/*  82:131 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  83:132 */       setEditado(false);
/*  84:133 */       limpiar();
/*  85:    */     }
/*  86:    */     catch (Exception e)
/*  87:    */     {
/*  88:135 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  89:136 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  90:    */     }
/*  91:138 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String eliminar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:148 */       this.servicioBroker.eliminar(getBroker());
/*  99:149 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:151 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 104:152 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 105:    */     }
/* 106:154 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String cargarDatos()
/* 110:    */   {
/* 111:163 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String limpiar()
/* 115:    */   {
/* 116:172 */     crearBroker();
/* 117:173 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Broker getBroker()
/* 121:    */   {
/* 122:190 */     return this.broker;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setBroker(Broker broker)
/* 126:    */   {
/* 127:200 */     this.broker = broker;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public LazyDataModel<Broker> getListaBroker()
/* 131:    */   {
/* 132:209 */     return this.listaBroker;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setListaBroker(LazyDataModel<Broker> listaBroker)
/* 136:    */   {
/* 137:219 */     this.listaBroker = listaBroker;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/* 141:    */   {
/* 142:228 */     if (this.listaTipoIdentificacion == null) {
/* 143:229 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/* 144:    */     }
/* 145:231 */     return this.listaTipoIdentificacion;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/* 149:    */   {
/* 150:241 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public DataTable getDtBroker()
/* 154:    */   {
/* 155:250 */     return this.dtBroker;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setDtBroker(DataTable dtBroker)
/* 159:    */   {
/* 160:260 */     this.dtBroker = dtBroker;
/* 161:    */   }
/* 162:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.polizas.configuracion.BrokerBean
 * JD-Core Version:    0.7.0.1
 */