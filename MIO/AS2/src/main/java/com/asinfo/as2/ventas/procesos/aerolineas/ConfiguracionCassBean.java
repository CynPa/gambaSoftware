/*   1:    */ package com.asinfo.as2.ventas.procesos.aerolineas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.aerolineas.ConfiguracionCass;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  11:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.JsfUtil;
/*  14:    */ import java.util.ArrayList;
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
/*  30:    */ public class ConfiguracionCassBean
/*  31:    */   extends PageControllerAS2
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   private ConfiguracionCass configuracionCass;
/*  35:    */   private LazyDataModel<ConfiguracionCass> listaConfiguracionCass;
/*  36:    */   @EJB
/*  37:    */   private ServicioGenerico<ConfiguracionCass> servicioConfiguracionCass;
/*  38:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  39:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  40:    */   private CuentaContable cuentaContable;
/*  41:    */   private DataTable dtConfiguracionCass;
/*  42:    */   private enumCuentaContableEditada cuentaContableEditada;
/*  43:    */   
/*  44:    */   private static enum enumCuentaContableEditada
/*  45:    */   {
/*  46: 52 */     VENTAS_CASS,  MANEJO_CARGA_TARIFA_CERO,  CUENTA_POR_COBRAR_CASS,  DISCOUNT,  COMMISION_AGENCY,  GUIA_COLLECT_X,  CREDITO_TRIBUTARIO_COMMISION,  IVA_RETENIDO,  RETENCION_FLETE_AGENCIA_VIAJE_CARGA;
/*  47:    */     
/*  48:    */     private enumCuentaContableEditada() {}
/*  49:    */   }
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 59 */     this.listaConfiguracionCass = new LazyDataModel()
/*  55:    */     {
/*  56:    */       private static final long serialVersionUID = 1L;
/*  57:    */       
/*  58:    */       public List<ConfiguracionCass> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */       {
/*  60: 66 */         List<ConfiguracionCass> lista = new ArrayList();
/*  61: 67 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  62:    */         
/*  63: 69 */         lista = ConfiguracionCassBean.this.servicioConfiguracionCass.obtenerListaPorPagina(ConfiguracionCass.class, startIndex, pageSize, sortField, ordenar, filters);
/*  64: 70 */         ConfiguracionCassBean.this.listaConfiguracionCass.setRowCount(ConfiguracionCassBean.this.servicioConfiguracionCass.contarPorCriterio(ConfiguracionCass.class, filters));
/*  65:    */         
/*  66: 72 */         return lista;
/*  67:    */       }
/*  68:    */     };
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String editar()
/*  72:    */   {
/*  73: 81 */     if (this.configuracionCass.getId() > 0)
/*  74:    */     {
/*  75: 82 */       List<String> listaCampos = new ArrayList();
/*  76: 83 */       listaCampos.add("ventasCass");
/*  77: 84 */       listaCampos.add("manejoCargaTarifaCero");
/*  78: 85 */       listaCampos.add("cuentaPorCobrarCass");
/*  79: 86 */       listaCampos.add("discount");
/*  80: 87 */       listaCampos.add("commissionAgency");
/*  81: 88 */       listaCampos.add("guiaCollectX");
/*  82: 89 */       listaCampos.add("creditoTributarioCommision");
/*  83: 90 */       listaCampos.add("ivaRetenido");
/*  84: 91 */       listaCampos.add("retencionFleteAgenciaViajeCarga");
/*  85: 92 */       this.configuracionCass = ((ConfiguracionCass)this.servicioConfiguracionCass.cargarDetalle(ConfiguracionCass.class, this.configuracionCass.getId(), listaCampos));
/*  86: 93 */       setEditado(true);
/*  87:    */     }
/*  88:    */     else
/*  89:    */     {
/*  90: 95 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  91:    */     }
/*  92: 97 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String guardar()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:104 */       this.servicioConfiguracionCass.guardar(this.configuracionCass);
/* 100:105 */       setEditado(false);
/* 101:106 */       limpiar();
/* 102:107 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 103:    */     }
/* 104:    */     catch (AS2Exception e)
/* 105:    */     {
/* 106:109 */       JsfUtil.addErrorMessage(e, "");
/* 107:    */     }
/* 108:    */     catch (Exception e)
/* 109:    */     {
/* 110:111 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 111:112 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 112:    */     }
/* 113:114 */     return null;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String eliminar()
/* 117:    */   {
/* 118:120 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String limpiar()
/* 122:    */   {
/* 123:125 */     this.configuracionCass = new ConfiguracionCass();
/* 124:126 */     this.configuracionCass.setEditado(true);
/* 125:127 */     this.configuracionCass.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 126:128 */     this.configuracionCass.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 127:129 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String cargarDatos()
/* 131:    */   {
/* 132:135 */     return null;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void cargarCuentaContable(SelectEvent event)
/* 136:    */   {
/* 137:139 */     this.cuentaContable = ((CuentaContable)event.getObject());
/* 138:140 */     switch (2.$SwitchMap$com$asinfo$as2$ventas$procesos$aerolineas$ConfiguracionCassBean$enumCuentaContableEditada[this.cuentaContableEditada.ordinal()])
/* 139:    */     {
/* 140:    */     case 1: 
/* 141:143 */       this.configuracionCass.setVentasCass(this.cuentaContable);
/* 142:144 */       break;
/* 143:    */     case 2: 
/* 144:146 */       this.configuracionCass.setManejoCargaTarifaCero(this.cuentaContable);
/* 145:147 */       break;
/* 146:    */     case 3: 
/* 147:150 */       this.configuracionCass.setCuentaPorCobrarCass(this.cuentaContable);
/* 148:151 */       break;
/* 149:    */     case 4: 
/* 150:154 */       this.configuracionCass.setDiscount(this.cuentaContable);
/* 151:155 */       break;
/* 152:    */     case 5: 
/* 153:158 */       this.configuracionCass.setCommissionAgency(this.cuentaContable);
/* 154:159 */       break;
/* 155:    */     case 6: 
/* 156:162 */       this.configuracionCass.setGuiaCollectX(this.cuentaContable);
/* 157:163 */       break;
/* 158:    */     case 7: 
/* 159:165 */       this.configuracionCass.setCreditoTributarioCommision(this.cuentaContable);
/* 160:166 */       break;
/* 161:    */     case 8: 
/* 162:168 */       this.configuracionCass.setIvaRetenido(this.cuentaContable);
/* 163:169 */       break;
/* 164:    */     case 9: 
/* 165:171 */       this.configuracionCass.setRetencionFleteAgenciaViajeCarga(this.cuentaContable);
/* 166:172 */       break;
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public ConfiguracionCass getConfiguracionCass()
/* 171:    */   {
/* 172:180 */     return this.configuracionCass == null ? (this.configuracionCass = new ConfiguracionCass()) : this.configuracionCass;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setConfiguracionCass(ConfiguracionCass configuracionCass)
/* 176:    */   {
/* 177:184 */     this.configuracionCass = configuracionCass;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 181:    */   {
/* 182:188 */     return this.listaCuentaContableBean;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 186:    */   {
/* 187:192 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public CuentaContable getCuentaContable()
/* 191:    */   {
/* 192:196 */     return this.cuentaContable;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 196:    */   {
/* 197:200 */     this.cuentaContable = cuentaContable;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public enumCuentaContableEditada getCuentaContableEditada()
/* 201:    */   {
/* 202:204 */     return this.cuentaContableEditada;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setCuentaContableEditada(enumCuentaContableEditada cuentaContableEditada)
/* 206:    */   {
/* 207:208 */     this.cuentaContableEditada = cuentaContableEditada;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public LazyDataModel<ConfiguracionCass> getListaConfiguracionCass()
/* 211:    */   {
/* 212:212 */     return this.listaConfiguracionCass;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setListaConfiguracionCass(LazyDataModel<ConfiguracionCass> listaConfiguracionCass)
/* 216:    */   {
/* 217:216 */     this.listaConfiguracionCass = listaConfiguracionCass;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public DataTable getDtConfiguracionCass()
/* 221:    */   {
/* 222:220 */     return this.dtConfiguracionCass;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setDtConfiguracionCass(DataTable dtConfiguracionCass)
/* 226:    */   {
/* 227:224 */     this.dtConfiguracionCass = dtConfiguracionCass;
/* 228:    */   }
/* 229:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.aerolineas.ConfiguracionCassBean
 * JD-Core Version:    0.7.0.1
 */