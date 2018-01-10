/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   6:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   8:    */ import com.asinfo.as2.entities.NotaFacturaCliente;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.JsfUtil;
/*  18:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  19:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  20:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.component.datatable.DataTable;
/*  30:    */ import org.primefaces.context.RequestContext;
/*  31:    */ import org.primefaces.model.LazyDataModel;
/*  32:    */ import org.primefaces.model.SortOrder;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class AprobarNotaCreditoClienteBean
/*  37:    */   extends PageControllerAS2
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 7447438187503589426L;
/*  40:    */   @EJB
/*  41:    */   private ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  42:    */   @EJB
/*  43:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  44:    */   protected FacturaCliente notaCreditoCliente;
/*  45:    */   protected LazyDataModel<FacturaCliente> listaNotaCreditoCliente;
/*  46:    */   protected NotaFacturaCliente notaFacturaCliente;
/*  47:    */   private DataTable dtNotaCreditoCliente;
/*  48:    */   private DataTable dtDetalleNotaCreditoCliente;
/*  49:    */   private DataTable dtImpuestoDetalleNC;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54:    */     try
/*  55:    */     {
/*  56: 78 */       limpiar();
/*  57: 79 */       this.listaNotaCreditoCliente = new LazyDataModel()
/*  58:    */       {
/*  59:    */         private static final long serialVersionUID = 4780083578367601484L;
/*  60:    */         
/*  61:    */         public List<FacturaCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  62:    */         {
/*  63: 86 */           List<FacturaCliente> lista = new ArrayList();
/*  64:    */           
/*  65: 88 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  66:    */           
/*  67: 90 */           filters.put("OR~est~01~estado", Estado.ELABORADO.toString());
/*  68: 91 */           filters.put("OR~est~02~estado", Estado.APROBADO_PARCIAL.toString());
/*  69: 92 */           filters.put("OR~doc~01~documento.documentoBase", DocumentoBase.NOTA_CREDITO_CLIENTE.toString());
/*  70: 93 */           filters.put("OR~doc~02~documento.documentoBase", DocumentoBase.DEVOLUCION_CLIENTE.toString());
/*  71: 94 */           lista = AprobarNotaCreditoClienteBean.this.servicioNotaCreditoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  72:    */           
/*  73: 96 */           AprobarNotaCreditoClienteBean.this.listaNotaCreditoCliente.setRowCount(AprobarNotaCreditoClienteBean.this.servicioNotaCreditoCliente.contarPorCriterio(filters));
/*  74: 97 */           return lista;
/*  75:    */         }
/*  76:    */       };
/*  77:    */     }
/*  78:    */     catch (Exception e)
/*  79:    */     {
/*  80:103 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  81:104 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String editar()
/*  86:    */   {
/*  87:115 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  88:116 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String guardar()
/*  92:    */   {
/*  93:126 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  94:127 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String eliminar()
/*  98:    */   {
/*  99:137 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 100:138 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String limpiar()
/* 104:    */   {
/* 105:148 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String cargarDatos()
/* 109:    */   {
/* 110:158 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<DetalleFacturaCliente> getListaDetalleFacturaCliente()
/* 114:    */   {
/* 115:167 */     List<DetalleFacturaCliente> detalle = new ArrayList();
/* 116:168 */     if (this.notaCreditoCliente != null) {
/* 117:169 */       for (DetalleFacturaCliente dfc : this.notaCreditoCliente.getListaDetalleFacturaCliente()) {
/* 118:170 */         if (!dfc.isEliminado()) {
/* 119:171 */           detalle.add(dfc);
/* 120:    */         }
/* 121:    */       }
/* 122:    */     }
/* 123:175 */     return detalle;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<ImpuestoProductoFacturaCliente> getListaImpuestoProductoNC()
/* 127:    */   {
/* 128:185 */     List<ImpuestoProductoFacturaCliente> listaImpuestoProductoFacturaClientes = new ArrayList();
/* 129:187 */     for (DetalleFacturaCliente dfc : this.notaCreditoCliente.getListaDetalleFacturaCliente()) {
/* 130:189 */       for (ImpuestoProductoFacturaCliente ipfc : dfc.getListaImpuestoProductoFacturaCliente()) {
/* 131:190 */         if (!ipfc.isEliminado()) {
/* 132:191 */           listaImpuestoProductoFacturaClientes.add(ipfc);
/* 133:    */         }
/* 134:    */       }
/* 135:    */     }
/* 136:197 */     return listaImpuestoProductoFacturaClientes;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public FacturaCliente getNotaCreditoCliente()
/* 140:    */   {
/* 141:206 */     return this.notaCreditoCliente;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setNotaCreditoCliente(FacturaCliente notaCreditoCliente)
/* 145:    */   {
/* 146:216 */     this.notaCreditoCliente = notaCreditoCliente;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public LazyDataModel<FacturaCliente> getListaNotaCreditoCliente()
/* 150:    */   {
/* 151:225 */     return this.listaNotaCreditoCliente;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setListaNotaCreditoCliente(LazyDataModel<FacturaCliente> listaNotaCreditoCliente)
/* 155:    */   {
/* 156:235 */     this.listaNotaCreditoCliente = listaNotaCreditoCliente;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public DataTable getDtNotaCreditoCliente()
/* 160:    */   {
/* 161:244 */     return this.dtNotaCreditoCliente;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setDtNotaCreditoCliente(DataTable dtNotaCreditoCliente)
/* 165:    */   {
/* 166:254 */     this.dtNotaCreditoCliente = dtNotaCreditoCliente;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public DataTable getDtDetalleNotaCreditoCliente()
/* 170:    */   {
/* 171:263 */     return this.dtDetalleNotaCreditoCliente;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setDtDetalleNotaCreditoCliente(DataTable dtDetalleNotaCreditoCliente)
/* 175:    */   {
/* 176:273 */     this.dtDetalleNotaCreditoCliente = dtDetalleNotaCreditoCliente;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public DataTable getDtImpuestoDetalleNC()
/* 180:    */   {
/* 181:282 */     return this.dtImpuestoDetalleNC;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setDtImpuestoDetalleNC(DataTable dtImpuestoDetalleNC)
/* 185:    */   {
/* 186:292 */     this.dtImpuestoDetalleNC = dtImpuestoDetalleNC;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private void crearNotaFacturaCliente()
/* 190:    */   {
/* 191:296 */     this.notaFacturaCliente = new NotaFacturaCliente();
/* 192:297 */     this.notaFacturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 193:298 */     this.notaFacturaCliente.setIdSucursal(AppUtil.getSucursal().getId());
/* 194:299 */     this.notaFacturaCliente.setFacturaCliente(this.notaCreditoCliente);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void cargarDetalles()
/* 198:    */   {
/* 199:303 */     this.notaCreditoCliente = ((FacturaCliente)this.dtNotaCreditoCliente.getRowData());
/* 200:    */     try
/* 201:    */     {
/* 202:305 */       this.notaCreditoCliente = this.servicioFacturaCliente.cargarDetalle(this.notaCreditoCliente.getId());
/* 203:306 */       crearNotaFacturaCliente();
/* 204:    */     }
/* 205:    */     catch (ExcepcionAS2Ventas e)
/* 206:    */     {
/* 207:308 */       e.printStackTrace();
/* 208:309 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void aprobarNotaCreditoCliente()
/* 213:    */   {
/* 214:314 */     this.notaCreditoCliente.setEstado(Estado.PROCESADO);
/* 215:    */     try
/* 216:    */     {
/* 217:316 */       this.notaFacturaCliente.setDescripcion("Aprobar: " + this.notaFacturaCliente.getDescripcion());
/* 218:317 */       this.servicioNotaCreditoCliente.aprobar(this.notaCreditoCliente, AppUtil.getUsuarioEnSesion(), this.notaFacturaCliente);
/* 219:318 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 220:    */       
/* 221:320 */       RequestContext context = RequestContext.getCurrentInstance();
/* 222:321 */       context.execute("PF('detalleNotaCreditoDialog').hide();");
/* 223:322 */       this.notaFacturaCliente = null;
/* 224:    */     }
/* 225:    */     catch (ExcepcionAS2Financiero e)
/* 226:    */     {
/* 227:324 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion());
/* 228:325 */       if (e.getMessage() != null) {
/* 229:326 */         strMensaje = strMensaje + " " + e.getMessage();
/* 230:    */       }
/* 231:328 */       addInfoMessage(strMensaje);
/* 232:329 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 233:    */     }
/* 234:    */     catch (ExcepcionAS2 e)
/* 235:    */     {
/* 236:331 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion());
/* 237:332 */       if (e.getMessage() != null) {
/* 238:333 */         strMensaje = strMensaje + " " + e.getMessage();
/* 239:    */       }
/* 240:335 */       addInfoMessage(strMensaje);
/* 241:336 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 242:    */     }
/* 243:    */     catch (AS2Exception e)
/* 244:    */     {
/* 245:338 */       JsfUtil.addErrorMessage(e, "");
/* 246:339 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 247:    */     }
/* 248:    */     catch (Exception e)
/* 249:    */     {
/* 250:341 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 251:342 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void rechazarNotaCreditoCliente()
/* 256:    */   {
/* 257:    */     try
/* 258:    */     {
/* 259:349 */       this.notaFacturaCliente.setDescripcion("Rechazar: " + this.notaFacturaCliente.getDescripcion());
/* 260:350 */       this.servicioNotaCreditoCliente.rechazar(this.notaCreditoCliente, AppUtil.getUsuarioEnSesion(), this.notaFacturaCliente);
/* 261:351 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 262:    */       
/* 263:353 */       RequestContext context = RequestContext.getCurrentInstance();
/* 264:354 */       context.execute("PF('detalleNotaCreditoDialog').hide();");
/* 265:    */     }
/* 266:    */     catch (AS2Exception e)
/* 267:    */     {
/* 268:356 */       e.printStackTrace();
/* 269:357 */       JsfUtil.addErrorMessage(e, "");
/* 270:    */     }
/* 271:    */     catch (Exception e)
/* 272:    */     {
/* 273:359 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 274:360 */       LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE Exception", e);
/* 275:361 */       e.printStackTrace();
/* 276:    */     }
/* 277:    */   }
/* 278:    */   
/* 279:    */   public NotaFacturaCliente getNotaFacturaCliente()
/* 280:    */   {
/* 281:366 */     return this.notaFacturaCliente;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setNotaFacturaCliente(NotaFacturaCliente notaFacturaCliente)
/* 285:    */   {
/* 286:370 */     this.notaFacturaCliente = notaFacturaCliente;
/* 287:    */   }
/* 288:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.AprobarNotaCreditoClienteBean
 * JD-Core Version:    0.7.0.1
 */