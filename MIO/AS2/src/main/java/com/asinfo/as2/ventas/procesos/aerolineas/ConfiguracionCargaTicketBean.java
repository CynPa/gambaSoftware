/*   1:    */ package com.asinfo.as2.ventas.procesos.aerolineas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.aerolineas.ConfiguracionCargaTicket;
/*   7:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  10:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ManagedProperty;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import javax.faces.model.SelectItem;
/*  20:    */ import org.primefaces.event.SelectEvent;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ConfiguracionCargaTicketBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<ConfiguracionCargaTicket> servicioConfiguracionCargaTicket;
/*  32:    */   private LazyDataModel<ConfiguracionCargaTicket> listaCatalogos;
/*  33:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  34:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  35:    */   private ConfiguracionCargaTicket configuracionCargaTicket;
/*  36:    */   private List<CuentaContable> listCuentasContables;
/*  37:    */   private List<SelectItem> listaOpercionEnum;
/*  38:    */   
/*  39:    */   public String crear()
/*  40:    */   {
/*  41: 58 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  42: 59 */     return "";
/*  43:    */   }
/*  44:    */   
/*  45:    */   @PostConstruct
/*  46:    */   public void init()
/*  47:    */   {
/*  48: 64 */     this.listaCatalogos = new LazyDataModel()
/*  49:    */     {
/*  50:    */       private static final long serialVersionUID = 1L;
/*  51:    */       
/*  52:    */       public List<ConfiguracionCargaTicket> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  53:    */       {
/*  54: 71 */         List<ConfiguracionCargaTicket> lista = new ArrayList();
/*  55:    */         
/*  56: 73 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  57:    */         try
/*  58:    */         {
/*  59: 76 */           lista = ConfiguracionCargaTicketBean.this.servicioConfiguracionCargaTicket.obtenerListaPorPagina(ConfiguracionCargaTicket.class, startIndex, pageSize, sortField, ordenar, filters);
/*  60:    */         }
/*  61:    */         catch (Exception e)
/*  62:    */         {
/*  63: 80 */           e.printStackTrace();
/*  64:    */         }
/*  65: 82 */         ConfiguracionCargaTicketBean.this.listaCatalogos.setRowCount(ConfiguracionCargaTicketBean.this.servicioConfiguracionCargaTicket.contarPorCriterio(ConfiguracionCargaTicket.class, filters));
/*  66: 83 */         return lista;
/*  67:    */       }
/*  68: 85 */     };
/*  69: 86 */     this.configuracionCargaTicket = new ConfiguracionCargaTicket();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void cargarCuentaContableTKT1(SelectEvent event)
/*  73:    */   {
/*  74: 90 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/*  75: 91 */     this.configuracionCargaTicket.setCuentaContableTKT1(cuentaContable);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void cargarCuentaContableTKT2(SelectEvent event)
/*  79:    */   {
/*  80: 95 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/*  81: 96 */     this.configuracionCargaTicket.setCuentaContableTKT2(cuentaContable);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String editar()
/*  85:    */   {
/*  86:101 */     if (this.configuracionCargaTicket != null)
/*  87:    */     {
/*  88:102 */       List<String> listCamp = new ArrayList();
/*  89:103 */       listCamp.add("cuentaContableTKT1");
/*  90:104 */       listCamp.add("cuentaContableTKT2");
/*  91:105 */       this.configuracionCargaTicket = ((ConfiguracionCargaTicket)this.servicioConfiguracionCargaTicket.cargarDetalle(ConfiguracionCargaTicket.class, this.configuracionCargaTicket
/*  92:106 */         .getId(), listCamp));
/*  93:107 */       setEditado(true);
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:110 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  98:    */     }
/*  99:112 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String guardar()
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:118 */       this.servicioConfiguracionCargaTicket.guardar(this.configuracionCargaTicket);
/* 107:119 */       setEditado(false);
/* 108:    */     }
/* 109:    */     catch (AS2Exception e)
/* 110:    */     {
/* 111:121 */       e.printStackTrace();
/* 112:    */     }
/* 113:123 */     return null;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String eliminar()
/* 117:    */   {
/* 118:129 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String limpiar()
/* 122:    */   {
/* 123:135 */     return null;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String cargarDatos()
/* 127:    */   {
/* 128:141 */     return null;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public LazyDataModel<ConfiguracionCargaTicket> getListaCatalogos()
/* 132:    */   {
/* 133:145 */     return this.listaCatalogos;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setListaCatalogos(LazyDataModel<ConfiguracionCargaTicket> listaCatalogos)
/* 137:    */   {
/* 138:149 */     this.listaCatalogos = listaCatalogos;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<CuentaContable> getListCuentasContables()
/* 142:    */   {
/* 143:153 */     if (this.listCuentasContables == null) {
/* 144:154 */       this.listCuentasContables = new ArrayList();
/* 145:    */     }
/* 146:155 */     return this.listCuentasContables;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setListCuentasContables(List<CuentaContable> listCuentasContables)
/* 150:    */   {
/* 151:159 */     this.listCuentasContables = listCuentasContables;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public ConfiguracionCargaTicket getCatalogoConfiguracionTicket()
/* 155:    */   {
/* 156:163 */     return this.configuracionCargaTicket;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setCatalogoConfiguracionTicket(ConfiguracionCargaTicket configuracionCargaTicket)
/* 160:    */   {
/* 161:167 */     this.configuracionCargaTicket = configuracionCargaTicket;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 165:    */   {
/* 166:171 */     return this.listaCuentaContableBean;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 170:    */   {
/* 171:175 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public List<SelectItem> getListaOpercionEnum()
/* 175:    */   {
/* 176:179 */     if (this.listaOpercionEnum == null)
/* 177:    */     {
/* 178:180 */       this.listaOpercionEnum = new ArrayList();
/* 179:181 */       this.listaOpercionEnum.add(new SelectItem(OperacionEnum.MENOR, OperacionEnum.MENOR.toString()));
/* 180:182 */       this.listaOpercionEnum.add(new SelectItem(OperacionEnum.MAYOR_IGUAL, OperacionEnum.MAYOR_IGUAL.toString()));
/* 181:183 */       this.listaOpercionEnum.add(new SelectItem(OperacionEnum.IGUAL, OperacionEnum.IGUAL.toString()));
/* 182:    */     }
/* 183:185 */     return this.listaOpercionEnum;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setListaOpercionEnum(List<SelectItem> listaOpercionEnum)
/* 187:    */   {
/* 188:189 */     this.listaOpercionEnum = listaOpercionEnum;
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.aerolineas.ConfiguracionCargaTicketBean
 * JD-Core Version:    0.7.0.1
 */