/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioEspecialidad;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   6:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.Especialidad;
/*   8:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   9:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  12:    */ import com.asinfo.as2.entities.Producto;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  15:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.JsfUtil;
/*  20:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioEmisionBono;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.annotation.PostConstruct;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import javax.faces.component.html.HtmlInputText;
/*  30:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ import org.primefaces.component.datatable.DataTable;
/*  33:    */ import org.primefaces.model.LazyDataModel;
/*  34:    */ import org.primefaces.model.SortOrder;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class EmisionBonoBean
/*  39:    */   extends FacturaClienteAgilBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 4631720976613845483L;
/*  42:    */   private LazyDataModel<DetalleFacturaCliente> listaBono;
/*  43:    */   private LazyDataModel<DetalleFacturaCliente> listaBonoFiltrada;
/*  44:    */   private DataTable dtBono;
/*  45:    */   private DataTable dtDetalleBono;
/*  46:    */   private DetalleFacturaCliente bonoSeleccionado;
/*  47:    */   @EJB
/*  48:    */   private ServicioEmisionBono servicioBono;
/*  49:    */   @EJB
/*  50:    */   private ServicioPersonaResponsable servicioResponsableSalidaMercaderia;
/*  51:    */   @EJB
/*  52:    */   private ServicioEspecialidad servicioEspecialidad;
/*  53:    */   
/*  54:    */   @PostConstruct
/*  55:    */   public void init()
/*  56:    */   {
/*  57: 71 */     getListaProductoBean().setTipoProducto(TipoProducto.SERVICIO);
/*  58: 72 */     getListaProductoBean().setIndicadorVenta(true);
/*  59: 73 */     getListaProductoBean().setActivo(true);
/*  60: 75 */     if (isEditado()) {
/*  61: 76 */       limpiar();
/*  62:    */     }
/*  63:    */     try
/*  64:    */     {
/*  65: 81 */       this.listaBono = new LazyDataModel()
/*  66:    */       {
/*  67:    */         private static final long serialVersionUID = 1L;
/*  68:    */         
/*  69:    */         public List<DetalleFacturaCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  70:    */         {
/*  71: 88 */           List<DetalleFacturaCliente> lista = new ArrayList();
/*  72: 89 */           filters.put("indicadorBono", "true");
/*  73: 91 */           if (EmisionBonoBean.this.idFacturaCliente != null)
/*  74:    */           {
/*  75: 92 */             filters.put("facturaCliente.idFacturaCliente", "" + EmisionBonoBean.this.idFacturaCliente);
/*  76: 93 */             EmisionBonoBean.this.idFacturaCliente = null;
/*  77:    */           }
/*  78: 96 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  79:    */           
/*  80: 98 */           lista = EmisionBonoBean.this.servicioBono.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  81: 99 */           EmisionBonoBean.this.listaBono.setRowCount(EmisionBonoBean.this.servicioBono.contarPorCriterio(filters));
/*  82:100 */           return lista;
/*  83:    */         }
/*  84:    */       };
/*  85:    */     }
/*  86:    */     catch (Exception e)
/*  87:    */     {
/*  88:106 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  89:107 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String eliminar()
/*  94:    */   {
/*  95:114 */     if (this.bonoSeleccionado.getId() != 0)
/*  96:    */     {
/*  97:115 */       this.servicioBono.eliminarAnularBono(this.bonoSeleccionado);
/*  98:116 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  99:    */     }
/* 100:    */     else
/* 101:    */     {
/* 102:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 103:    */     }
/* 104:121 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/* 108:    */   {
/* 109:129 */     DetalleFacturaCliente dfc = (DetalleFacturaCliente)getDtDetalleBono().getRowData();
/* 110:130 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 111:131 */     if (this.facturaCliente.getEmpresa() != null) {
/* 112:132 */       dfc.setEmpresaBono(this.facturaCliente.getEmpresa());
/* 113:    */     }
/* 114:134 */     dfc.setCantidad(BigDecimal.ONE);
/* 115:135 */     dfc.setIndicadorBono(true);
/* 116:136 */     dfc.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 117:137 */     dfc.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 118:    */     try
/* 119:    */     {
/* 120:139 */       cargarProductoDesdeCodigoEnDetalle(codigo, dfc);
/* 121:140 */       totalizar();
/* 122:    */     }
/* 123:    */     catch (ExcepcionAS2 e)
/* 124:    */     {
/* 125:142 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 126:143 */       dfc.getProducto().setCodigo("");
/* 127:144 */       dfc.getProducto().setNombre("");
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String editar()
/* 132:    */   {
/* 133:151 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 134:152 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String guardar()
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:157 */       this.servicioBono.validarBonos(this.facturaCliente);
/* 142:158 */       super.guardar();
/* 143:    */     }
/* 144:    */     catch (AS2Exception e)
/* 145:    */     {
/* 146:160 */       JsfUtil.addErrorMessage(e, "");
/* 147:161 */       e.printStackTrace();
/* 148:    */     }
/* 149:164 */     return "";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void copiarMedicos()
/* 153:    */   {
/* 154:168 */     PersonaResponsable medico = null;
/* 155:169 */     for (DetalleFacturaCliente bono : getDetalleFacturaCliente()) {
/* 156:170 */       if (bono.getMedico() != null)
/* 157:    */       {
/* 158:171 */         medico = bono.getMedico();
/* 159:172 */         break;
/* 160:    */       }
/* 161:    */     }
/* 162:176 */     if (medico != null) {
/* 163:177 */       for (DetalleFacturaCliente bono : getDetalleFacturaCliente())
/* 164:    */       {
/* 165:178 */         bono.setMedico(medico);
/* 166:179 */         if (medico.getEspecialidad() != null) {
/* 167:180 */           bono.setTipoBono(medico.getEspecialidad());
/* 168:    */         }
/* 169:    */       }
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String limpiar()
/* 174:    */   {
/* 175:188 */     super.limpiar();
/* 176:189 */     this.facturaCliente.setEmpresa(null);
/* 177:190 */     this.facturaCliente.setDireccionEmpresa(null);
/* 178:191 */     this.facturaCliente.setEmail(null);
/* 179:192 */     return "";
/* 180:    */   }
/* 181:    */   
/* 182:    */   public DetalleFacturaCliente agregarDetalleFactura()
/* 183:    */   {
/* 184:196 */     DetalleFacturaCliente dfc = super.agregarDetalleFactura();
/* 185:197 */     return dfc;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public DetalleFacturaCliente cargarProducto()
/* 189:    */   {
/* 190:202 */     DetalleFacturaCliente dfc = super.cargarProducto();
/* 191:203 */     BigDecimal cantidad = dfc.getCantidad();
/* 192:204 */     getListaProductoBean().setProducto(dfc.getProducto());
/* 193:205 */     int parteEntera = cantidad.intValue() - 1;
/* 194:206 */     for (int i = 0; i < parteEntera; i++)
/* 195:    */     {
/* 196:207 */       DetalleFacturaCliente dfcNuevo = new DetalleFacturaCliente();
/* 197:208 */       dfcNuevo.setCantidad(BigDecimal.ONE);
/* 198:209 */       dfcNuevo.setProducto(dfc.getProducto());
/* 199:210 */       actualizarProducto(dfcNuevo, dfc.getProducto());
/* 200:211 */       dfcNuevo.setUnidadVenta(dfc.getUnidadVenta());
/* 201:212 */       dfcNuevo.setPrecio(dfc.getPrecio());
/* 202:213 */       dfcNuevo.setIndicadorBono(true);
/* 203:214 */       dfcNuevo.setEmpresaBono(this.facturaCliente.getEmpresa());
/* 204:215 */       dfcNuevo.setFacturaCliente(this.facturaCliente);
/* 205:216 */       dfcNuevo.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 206:217 */       dfcNuevo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 207:218 */       this.facturaCliente.getListaDetalleFacturaCliente().add(dfcNuevo);
/* 208:    */     }
/* 209:220 */     dfc.setCantidad(BigDecimal.ONE);
/* 210:221 */     dfc.setIndicadorBono(true);
/* 211:222 */     dfc.setEmpresaBono(this.facturaCliente.getEmpresa());
/* 212:223 */     dfc.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 213:224 */     dfc.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 214:    */     
/* 215:    */ 
/* 216:227 */     totalizar();
/* 217:228 */     return dfc;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List<PersonaResponsable> autocompletarMedico(String consulta)
/* 221:    */   {
/* 222:233 */     return this.servicioResponsableSalidaMercaderia.autocompletarMedico(consulta, AppUtil.getOrganizacion().getIdOrganizacion());
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List<Especialidad> autocompletarTipoBono(String consulta)
/* 226:    */   {
/* 227:237 */     return this.servicioEspecialidad.autocompletarEspecialidad(consulta, AppUtil.getOrganizacion().getIdOrganizacion());
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void cargarTipoBono()
/* 231:    */   {
/* 232:241 */     DetalleFacturaCliente detalleFacturaCliente = (DetalleFacturaCliente)this.dtDetalleBono.getRowData();
/* 233:    */     
/* 234:243 */     PersonaResponsable medico = detalleFacturaCliente.getMedico();
/* 235:245 */     if (medico.getEspecialidad() != null) {
/* 236:246 */       detalleFacturaCliente.setTipoBono(medico.getEspecialidad());
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String eliminarDetalle()
/* 241:    */   {
/* 242:252 */     DetalleFacturaCliente detalleBono = (DetalleFacturaCliente)this.dtDetalleBono.getRowData();
/* 243:253 */     detalleBono.setEliminado(true);
/* 244:254 */     detalleBono.setCantidad(BigDecimal.ZERO);
/* 245:255 */     for (ImpuestoProductoFacturaCliente ipfc : detalleBono.getListaImpuestoProductoFacturaCliente()) {
/* 246:256 */       ipfc.setEliminado(true);
/* 247:    */     }
/* 248:258 */     totalizar();
/* 249:259 */     return "";
/* 250:    */   }
/* 251:    */   
/* 252:    */   public LazyDataModel<DetalleFacturaCliente> getListaBono()
/* 253:    */   {
/* 254:263 */     return this.listaBono;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setListaBono(LazyDataModel<DetalleFacturaCliente> listaBono)
/* 258:    */   {
/* 259:267 */     this.listaBono = listaBono;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public DataTable getDtBono()
/* 263:    */   {
/* 264:271 */     return this.dtBono;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setDtBono(DataTable dtBono)
/* 268:    */   {
/* 269:275 */     this.dtBono = dtBono;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public DataTable getDtDetalleBono()
/* 273:    */   {
/* 274:279 */     return this.dtDetalleBono;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setDtDetalleBono(DataTable dtDetalleBono)
/* 278:    */   {
/* 279:283 */     this.dtDetalleBono = dtDetalleBono;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public DetalleFacturaCliente getBonoSeleccionado()
/* 283:    */   {
/* 284:287 */     return this.bonoSeleccionado;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setBonoSeleccionado(DetalleFacturaCliente bonoSeleccionado)
/* 288:    */   {
/* 289:291 */     this.bonoSeleccionado = bonoSeleccionado;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public LazyDataModel<DetalleFacturaCliente> getListaBonoFiltrada()
/* 293:    */   {
/* 294:295 */     return this.listaBonoFiltrada;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setListaBonoFiltrada(LazyDataModel<DetalleFacturaCliente> listaBonoFiltrada)
/* 298:    */   {
/* 299:299 */     this.listaBonoFiltrada = listaBonoFiltrada;
/* 300:    */   }
/* 301:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.EmisionBonoBean
 * JD-Core Version:    0.7.0.1
 */