/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   6:    */ import com.asinfo.as2.entities.CuentaContable;
/*   7:    */ import com.asinfo.as2.entities.FormaPago;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ViewScoped;
/*  18:    */ import javax.faces.model.SelectItem;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class FormaPagoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -5229752920439210529L;
/*  30:    */   @EJB
/*  31:    */   private ServicioFormaPago servicioFormaPago;
/*  32:    */   private FormaPago formaPago;
/*  33:    */   private LazyDataModel<FormaPago> listaFormaPago;
/*  34:    */   private List<SelectItem> listaFormaPagoItem;
/*  35:    */   private List<FormaPago> listaFormaPagoCombo;
/*  36:    */   private CuentaContable cuentaContable;
/*  37:    */   private enumCuentaContableEditada cuentaContableEditada;
/*  38:    */   private DataTable dtCuentaContable;
/*  39:    */   private DataTable dtFormaPago;
/*  40:    */   
/*  41:    */   private static enum enumCuentaContableEditada
/*  42:    */   {
/*  43: 64 */     CUENTA_CONTABLE_COBRO,  CUENTA_CONTABLE_PAGO;
/*  44:    */     
/*  45:    */     private enumCuentaContableEditada() {}
/*  46:    */   }
/*  47:    */   
/*  48:    */   @PostConstruct
/*  49:    */   public void init()
/*  50:    */   {
/*  51: 74 */     this.listaFormaPago = new LazyDataModel()
/*  52:    */     {
/*  53:    */       private static final long serialVersionUID = 1L;
/*  54:    */       
/*  55:    */       public List<FormaPago> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  56:    */       {
/*  57: 81 */         List<FormaPago> lista = new ArrayList();
/*  58: 82 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  59:    */         
/*  60: 84 */         lista = FormaPagoBean.this.servicioFormaPago.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  61: 85 */         FormaPagoBean.this.listaFormaPago.setRowCount(FormaPagoBean.this.servicioFormaPago.contarPorCriterio(filters));
/*  62:    */         
/*  63: 87 */         return lista;
/*  64:    */       }
/*  65:    */     };
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String limpiar()
/*  69:    */   {
/*  70:100 */     crearFormaPago();
/*  71:101 */     return "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String guardar()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:112 */       this.servicioFormaPago.guardar(this.formaPago);
/*  79:113 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  80:114 */       limpiar();
/*  81:115 */       setEditado(false);
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85:117 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  86:118 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  87:    */     }
/*  88:120 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:131 */       this.servicioFormaPago.eliminar(this.formaPago);
/*  96:132 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:134 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 101:135 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 102:    */     }
/* 103:137 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String cargarDatos()
/* 107:    */   {
/* 108:146 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void cargarListaFormaPagoItem()
/* 112:    */   {
/* 113:154 */     if (this.listaFormaPagoItem == null)
/* 114:    */     {
/* 115:156 */       this.listaFormaPagoItem = new ArrayList();
/* 116:158 */       for (FormaPago formaPago : getListaFormaPago())
/* 117:    */       {
/* 118:159 */         int value = formaPago.getIdFormaPago();
/* 119:160 */         String label = formaPago.getNombre();
/* 120:161 */         SelectItem opcion = new SelectItem(Integer.valueOf(value), label);
/* 121:162 */         this.listaFormaPagoItem.add(opcion);
/* 122:    */       }
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String editar()
/* 127:    */   {
/* 128:175 */     if (getFormaPago().getId() > 0)
/* 129:    */     {
/* 130:176 */       this.formaPago = this.servicioFormaPago.cargarDetalle(getFormaPago().getId());
/* 131:177 */       setEditado(true);
/* 132:    */     }
/* 133:    */     else
/* 134:    */     {
/* 135:179 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 136:    */     }
/* 137:181 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void crearFormaPago()
/* 141:    */   {
/* 142:188 */     this.formaPago = new FormaPago();
/* 143:189 */     this.formaPago.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 144:190 */     this.formaPago.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void actualizarIndicadores(String indicador)
/* 148:    */   {
/* 149:194 */     if (("fuente".equals(indicador)) && (this.formaPago.isIndicadorRetencionFuente()))
/* 150:    */     {
/* 151:195 */       this.formaPago.setIndicadorRetencionIva(false);
/* 152:196 */       this.formaPago.setIndicadorDepositoAutomatico(false);
/* 153:197 */       this.formaPago.setIndicadorCheque(false);
/* 154:198 */       this.formaPago.setIndicadorTarjetaCredito(false);
/* 155:    */     }
/* 156:199 */     else if (("iva".equals(indicador)) && (this.formaPago.isIndicadorRetencionIva()))
/* 157:    */     {
/* 158:200 */       this.formaPago.setIndicadorRetencionFuente(false);
/* 159:201 */       this.formaPago.setIndicadorDepositoAutomatico(false);
/* 160:202 */       this.formaPago.setIndicadorCheque(false);
/* 161:203 */       this.formaPago.setIndicadorTarjetaCredito(false);
/* 162:    */     }
/* 163:204 */     else if (("deposito".equals(indicador)) && (this.formaPago.isIndicadorDepositoAutomatico()))
/* 164:    */     {
/* 165:205 */       this.formaPago.setIndicadorRetencionFuente(false);
/* 166:206 */       this.formaPago.setIndicadorRetencionIva(false);
/* 167:207 */       this.formaPago.setIndicadorCheque(false);
/* 168:208 */       this.formaPago.setIndicadorTarjetaCredito(false);
/* 169:    */     }
/* 170:209 */     else if (("cheque".equals(indicador)) && (this.formaPago.isIndicadorCheque()))
/* 171:    */     {
/* 172:210 */       this.formaPago.setIndicadorRetencionFuente(false);
/* 173:211 */       this.formaPago.setIndicadorRetencionIva(false);
/* 174:212 */       this.formaPago.setIndicadorDepositoAutomatico(false);
/* 175:213 */       this.formaPago.setIndicadorTarjetaCredito(false);
/* 176:    */     }
/* 177:214 */     else if (("tarjeta".equals(indicador)) && (this.formaPago.isIndicadorTarjetaCredito()))
/* 178:    */     {
/* 179:215 */       this.formaPago.setIndicadorRetencionFuente(false);
/* 180:216 */       this.formaPago.setIndicadorRetencionIva(false);
/* 181:217 */       this.formaPago.setIndicadorDepositoAutomatico(false);
/* 182:218 */       this.formaPago.setIndicadorCheque(false);
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public ServicioFormaPago getServicioFormaPagoBean()
/* 187:    */   {
/* 188:222 */     return this.servicioFormaPago;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setServicioFormaPagoBean(ServicioFormaPago servicioFormaPagoBean)
/* 192:    */   {
/* 193:226 */     this.servicioFormaPago = servicioFormaPagoBean;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public FormaPago getFormaPago()
/* 197:    */   {
/* 198:230 */     if (this.formaPago == null) {
/* 199:231 */       crearFormaPago();
/* 200:    */     }
/* 201:233 */     return this.formaPago;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setFormaPago(FormaPago formaPago)
/* 205:    */   {
/* 206:237 */     this.formaPago = formaPago;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public LazyDataModel<FormaPago> getListaFormaPago()
/* 210:    */   {
/* 211:242 */     return this.listaFormaPago;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setListaFormaPago(LazyDataModel<FormaPago> listaFormaPago)
/* 215:    */   {
/* 216:246 */     this.listaFormaPago = listaFormaPago;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public DataTable getDtFormaPago()
/* 220:    */   {
/* 221:250 */     return this.dtFormaPago;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setDtFormaPago(DataTable dtFormaPago)
/* 225:    */   {
/* 226:254 */     this.dtFormaPago = dtFormaPago;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public List<SelectItem> getListaFormaPagoItem()
/* 230:    */   {
/* 231:263 */     cargarListaFormaPagoItem();
/* 232:264 */     return this.listaFormaPagoItem;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setListaFormaPagoItem(List<SelectItem> listaFormaPagoItem)
/* 236:    */   {
/* 237:274 */     this.listaFormaPagoItem = listaFormaPagoItem;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public CuentaContable getCuentaContable()
/* 241:    */   {
/* 242:283 */     return this.cuentaContable;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 246:    */   {
/* 247:293 */     this.cuentaContable = cuentaContable;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public DataTable getDtCuentaContable()
/* 251:    */   {
/* 252:297 */     return this.dtCuentaContable;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 256:    */   {
/* 257:301 */     this.dtCuentaContable = dtCuentaContable;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public List<FormaPago> getListaFormaPagoCombo()
/* 261:    */   {
/* 262:310 */     if (this.listaFormaPagoCombo == null) {
/* 263:311 */       this.listaFormaPagoCombo = this.servicioFormaPago.obtenerListaCombo(null, false, null);
/* 264:    */     }
/* 265:313 */     return this.listaFormaPagoCombo;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setListaFormaPagoCombo(List<FormaPago> listaFormaPagoCombo)
/* 269:    */   {
/* 270:323 */     this.listaFormaPagoCombo = listaFormaPagoCombo;
/* 271:    */   }
/* 272:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.FormaPagoBean
 * JD-Core Version:    0.7.0.1
 */