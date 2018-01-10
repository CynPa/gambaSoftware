/*   1:    */ package com.asinfo.as2.polizas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   9:    */ import com.asinfo.as2.entities.polizas.Aseguradora;
/*  10:    */ import com.asinfo.as2.entities.polizas.Broker;
/*  11:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioAseguradora;
/*  12:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioBroker;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Collection;
/*  16:    */ import java.util.HashMap;
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
/*  30:    */ public class AseguradoraBean
/*  31:    */   extends PageControllerAS2
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   private ServicioAseguradora servicioAseguradora;
/*  36:    */   @EJB
/*  37:    */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  38:    */   @EJB
/*  39:    */   private ServicioBroker servicioBroker;
/*  40:    */   private Aseguradora aseguradora;
/*  41:    */   private Broker[] brokerSeleccionados;
/*  42:    */   private LazyDataModel<Aseguradora> listaAseguradora;
/*  43:    */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*  44:    */   private List<Broker> listaBrokersNoAsignados;
/*  45:    */   private DataTable dtAseguradora;
/*  46:    */   private DataTable dtBrokersAsignados;
/*  47:    */   
/*  48:    */   @PostConstruct
/*  49:    */   public void init()
/*  50:    */   {
/*  51: 84 */     this.listaAseguradora = new LazyDataModel()
/*  52:    */     {
/*  53:    */       private static final long serialVersionUID = 1L;
/*  54:    */       
/*  55:    */       public List<Aseguradora> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  56:    */       {
/*  57: 91 */         List<Aseguradora> lista = new ArrayList();
/*  58: 92 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  59:    */         
/*  60: 94 */         lista = AseguradoraBean.this.servicioAseguradora.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  61:    */         
/*  62: 96 */         AseguradoraBean.this.listaAseguradora.setRowCount(AseguradoraBean.this.servicioAseguradora.contarPorCriterio(filters));
/*  63: 97 */         return lista;
/*  64:    */       }
/*  65:    */     };
/*  66:    */   }
/*  67:    */   
/*  68:    */   private void crearAseguradora()
/*  69:    */   {
/*  70:111 */     this.aseguradora = new Aseguradora();
/*  71:112 */     this.aseguradora.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  72:113 */     this.aseguradora.setIdSucursal(AppUtil.getSucursal().getId());
/*  73:114 */     this.aseguradora.setTipoIdentificacion(new TipoIdentificacion());
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String editar()
/*  77:    */   {
/*  78:123 */     if (getAseguradora().getIdAseguradora() > 0) {
/*  79:124 */       setEditado(true);
/*  80:    */     } else {
/*  81:126 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  82:    */     }
/*  83:128 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String guardar()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:138 */       this.servicioAseguradora.guardar(getAseguradora());
/*  91:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  92:140 */       setEditado(false);
/*  93:141 */       limpiar();
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  98:144 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  99:    */     }
/* 100:146 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String eliminar()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:156 */       this.servicioAseguradora.eliminar(getAseguradora());
/* 108:157 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 109:    */     }
/* 110:    */     catch (Exception e)
/* 111:    */     {
/* 112:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 113:160 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 114:    */     }
/* 115:162 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String cargarDatos()
/* 119:    */   {
/* 120:171 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String limpiar()
/* 124:    */   {
/* 125:180 */     crearAseguradora();
/* 126:181 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String cargarBrokersNoAsignados()
/* 130:    */   {
/* 131:190 */     this.brokerSeleccionados = null;
/* 132:191 */     HashMap<Integer, Broker> hashMapBroker = new HashMap();
/* 133:192 */     for (Broker broker : this.servicioBroker.obtenerListaCombo("nombres", true, null)) {
/* 134:193 */       hashMapBroker.put(Integer.valueOf(broker.getId()), broker);
/* 135:    */     }
/* 136:196 */     for (Broker broker : getAseguradora().getListaBroker()) {
/* 137:197 */       hashMapBroker.remove(Integer.valueOf(broker.getId()));
/* 138:    */     }
/* 139:200 */     this.listaBrokersNoAsignados = new ArrayList(hashMapBroker.values());
/* 140:    */     
/* 141:202 */     return "";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String asignarBrokers()
/* 145:    */   {
/* 146:206 */     if (this.brokerSeleccionados != null) {
/* 147:207 */       for (Broker broker : this.brokerSeleccionados) {
/* 148:208 */         getAseguradora().getListaBroker().add(broker);
/* 149:    */       }
/* 150:    */     }
/* 151:211 */     return "";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String eliminarDetalle()
/* 155:    */   {
/* 156:215 */     Broker broker = (Broker)this.dtBrokersAsignados.getRowData();
/* 157:216 */     broker.setEliminado(true);
/* 158:217 */     getAseguradora().getListaBroker().remove(broker);
/* 159:    */     
/* 160:219 */     return "";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public List<Broker> getListaBrokers()
/* 164:    */   {
/* 165:236 */     List<Broker> lista = new ArrayList();
/* 166:237 */     for (Broker broker : getAseguradora().getListaBroker()) {
/* 167:238 */       if (!broker.isEliminado()) {
/* 168:239 */         lista.add(broker);
/* 169:    */       }
/* 170:    */     }
/* 171:242 */     return lista;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Aseguradora getAseguradora()
/* 175:    */   {
/* 176:251 */     if (this.aseguradora == null) {
/* 177:252 */       crearAseguradora();
/* 178:    */     }
/* 179:254 */     return this.aseguradora;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setAseguradora(Aseguradora aseguradora)
/* 183:    */   {
/* 184:264 */     this.aseguradora = aseguradora;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public LazyDataModel<Aseguradora> getListaAseguradora()
/* 188:    */   {
/* 189:273 */     return this.listaAseguradora;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setListaAseguradora(LazyDataModel<Aseguradora> listaAseguradora)
/* 193:    */   {
/* 194:283 */     this.listaAseguradora = listaAseguradora;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/* 198:    */   {
/* 199:292 */     if (this.listaTipoIdentificacion == null) {
/* 200:293 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/* 201:    */     }
/* 202:295 */     return this.listaTipoIdentificacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/* 206:    */   {
/* 207:305 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public DataTable getDtAseguradora()
/* 211:    */   {
/* 212:314 */     return this.dtAseguradora;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setDtAseguradora(DataTable dtAseguradora)
/* 216:    */   {
/* 217:324 */     this.dtAseguradora = dtAseguradora;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Broker[] getBrokerSeleccionados()
/* 221:    */   {
/* 222:333 */     return this.brokerSeleccionados;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setBrokerSeleccionados(Broker[] brokerSeleccionados)
/* 226:    */   {
/* 227:343 */     this.brokerSeleccionados = brokerSeleccionados;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<Broker> getListaBrokersNoAsignados()
/* 231:    */   {
/* 232:352 */     return this.listaBrokersNoAsignados;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setListaBrokersNoAsignados(List<Broker> listaBrokersNoAsignados)
/* 236:    */   {
/* 237:362 */     this.listaBrokersNoAsignados = listaBrokersNoAsignados;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public DataTable getDtBrokersAsignados()
/* 241:    */   {
/* 242:371 */     return this.dtBrokersAsignados;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setDtBrokersAsignados(DataTable dtBrokersAsignados)
/* 246:    */   {
/* 247:381 */     this.dtBrokersAsignados = dtBrokersAsignados;
/* 248:    */   }
/* 249:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.polizas.configuracion.AseguradoraBean
 * JD-Core Version:    0.7.0.1
 */