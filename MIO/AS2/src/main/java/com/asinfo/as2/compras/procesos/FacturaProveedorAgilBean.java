/*   1:    */ package com.asinfo.as2.compras.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.entities.CuentaContable;
/*   8:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  11:    */ import com.asinfo.as2.entities.GastoProductoFacturaProveedor;
/*  12:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  13:    */ import com.asinfo.as2.entities.Lote;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.entities.Producto;
/*  16:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  17:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  23:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  24:    */ import com.asinfo.as2.util.AppUtil;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.component.html.HtmlInputText;
/*  32:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  33:    */ import javax.validation.constraints.NotNull;
/*  34:    */ import javax.validation.constraints.Size;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ import org.primefaces.component.datatable.DataTable;
/*  37:    */ 
/*  38:    */ @ManagedBean
/*  39:    */ @ViewScoped
/*  40:    */ public class FacturaProveedorAgilBean
/*  41:    */   extends FacturaProveedorBaseBean
/*  42:    */ {
/*  43:    */   private static final long serialVersionUID = -3952545541765875552L;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioLote servicioLote;
/*  48:    */   private CuentaContable cuentaContable;
/*  49:    */   private DataTable dtCuentaContable;
/*  50:    */   @NotNull
/*  51:    */   private TipoIdentificacion tipoIdentificacionProveedor;
/*  52:    */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*  53:    */   private String identificacionProveedor;
/*  54:    */   @Size(min=10, max=20)
/*  55:    */   private String nombreProveedor;
/*  56:    */   @Size(min=2, max=50)
/*  57:    */   private String direccionProveedor;
/*  58:    */   @Size(max=13)
/*  59:    */   private String telefonoProveedor;
/*  60:    */   private List<Documento> listaDocumentoRecepcion;
/*  61:    */   private String numero;
/*  62:    */   
/*  63:    */   @PostConstruct
/*  64:    */   public void init()
/*  65:    */   {
/*  66: 85 */     super.init();
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected void obtenerFiltros(Map<String, String> filters)
/*  70:    */   {
/*  71: 95 */     if (this.numero != null)
/*  72:    */     {
/*  73: 96 */       filters.put("numero", this.numero);
/*  74: 97 */       this.numero = null;
/*  75:    */     }
/*  76:100 */     filters.put("documento.documentoBase", DocumentoBase.FACTURA_PROVEEDOR.toString());
/*  77:101 */     filters.put("documento.indicadorDocumentoExterior", "false");
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String limpiar()
/*  81:    */   {
/*  82:111 */     super.limpiar();
/*  83:    */     
/*  84:    */ 
/*  85:114 */     RecepcionProveedor recepcionProveedor = new RecepcionProveedor();
/*  86:115 */     recepcionProveedor.setNumero("");
/*  87:116 */     recepcionProveedor.setEstado(Estado.PROCESADO);
/*  88:117 */     getFacturaProveedor().setRecepcionProveedor(recepcionProveedor);
/*  89:    */     
/*  90:119 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String guardar()
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:131 */       getFacturaProveedor().getRecepcionProveedor().setFecha(getFacturaProveedor().getFecha());
/*  98:132 */       getFacturaProveedor().getRecepcionProveedor().setIdOrganizacion(getFacturaProveedor().getIdOrganizacion());
/*  99:133 */       getFacturaProveedor().getRecepcionProveedor().setSucursal(getFacturaProveedor().getSucursal());
/* 100:134 */       getFacturaProveedor().getRecepcionProveedor().setEmpresa(getFacturaProveedor().getEmpresa());
/* 101:135 */       getFacturaProveedor().getRecepcionProveedor().setDescripcion(getFacturaProveedor().getDescripcion());
/* 102:137 */       if (!getListaDocumentoRecepcion().isEmpty()) {
/* 103:138 */         getFacturaProveedor().getRecepcionProveedor().setDocumento((Documento)getListaDocumentoRecepcion().get(0));
/* 104:    */       }
/* 105:149 */       this.servicioFacturaProveedor.validarBodega(getFacturaProveedor());
/* 106:    */       
/* 107:151 */       super.guardar();
/* 108:    */     }
/* 109:    */     catch (ExcepcionAS2Inventario e)
/* 110:    */     {
/* 111:153 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 112:154 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE AGIL ", e);
/* 113:    */     }
/* 114:    */     catch (ExcepcionAS2 e)
/* 115:    */     {
/* 116:156 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 117:157 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE AGIL ", e);
/* 118:    */     }
/* 119:    */     catch (Exception e)
/* 120:    */     {
/* 121:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 122:160 */       LOG.error("Error al guardar factura proveedor agil ", e);
/* 123:    */     }
/* 124:163 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/* 128:    */   {
/* 129:173 */     DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)getDtDetalleFacturaProveedor().getRowData();
/* 130:    */     
/* 131:175 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 132:176 */     Producto producto = null;
/* 133:    */     try
/* 134:    */     {
/* 135:180 */       producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.FACTURA_PROVEEDOR);
/* 136:181 */       super.actualizarProducto(dfp, producto);
/* 137:182 */       actualizarInventarioProducto(dfp, null);
/* 138:    */     }
/* 139:    */     catch (ExcepcionAS2 e)
/* 140:    */     {
/* 141:    */       try
/* 142:    */       {
/* 143:186 */         Lote lote = this.servicioLote.buscarPorCodigo(codigo);
/* 144:187 */         producto = this.servicioProducto.buscarPorCodigo(lote.getProducto().getCodigo(), AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.FACTURA_PROVEEDOR);
/* 145:    */         
/* 146:189 */         super.actualizarProducto(dfp, producto);
/* 147:190 */         actualizarInventarioProducto(dfp, lote);
/* 148:    */       }
/* 149:    */       catch (ExcepcionAS2 e2)
/* 150:    */       {
/* 151:193 */         addInfoMessage(getLanguageController().getMensaje(e2.getCodigoExcepcion()) + e2.getMessage());
/* 152:    */       }
/* 153:195 */       if (producto == null) {
/* 154:196 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   private InventarioProducto actualizarInventarioProducto(DetalleFacturaProveedor dfp, Lote lote)
/* 160:    */   {
/* 161:228 */     return null;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String crearProveedor()
/* 165:    */   {
/* 166:239 */     return "";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void cargarProducto()
/* 170:    */   {
/* 171:249 */     super.cargarProducto();
/* 172:252 */     for (DetalleFacturaProveedor dfp : getFacturaProveedor().getListaDetalleFacturaProveedor()) {
/* 173:253 */       actualizarInventarioProducto(dfp, null);
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void cargarCuentaContable()
/* 178:    */   {
/* 179:258 */     this.cuentaContable = ((CuentaContable)this.dtCuentaContable.getRowData());
/* 180:259 */     getGastoProductoFacturaProveedorSeleccionado().setCuentaContableGasto(this.cuentaContable);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public TipoIdentificacion getTipoIdentificacionProveedor()
/* 184:    */   {
/* 185:270 */     return this.tipoIdentificacionProveedor;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setTipoIdentificacionProveedor(TipoIdentificacion tipoIdentificacionProveedor)
/* 189:    */   {
/* 190:280 */     this.tipoIdentificacionProveedor = tipoIdentificacionProveedor;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/* 194:    */   {
/* 195:289 */     if (this.listaTipoIdentificacion == null) {
/* 196:290 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/* 197:    */     }
/* 198:292 */     return this.listaTipoIdentificacion;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/* 202:    */   {
/* 203:302 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String getNombreProveedor()
/* 207:    */   {
/* 208:311 */     return this.nombreProveedor;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setNombreProveedor(String nombreProveedor)
/* 212:    */   {
/* 213:321 */     this.nombreProveedor = nombreProveedor;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String getDireccionProveedor()
/* 217:    */   {
/* 218:330 */     return this.direccionProveedor;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setDireccionProveedor(String direccionProveedor)
/* 222:    */   {
/* 223:340 */     this.direccionProveedor = direccionProveedor;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String getTelefonoProveedor()
/* 227:    */   {
/* 228:349 */     return this.telefonoProveedor;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setTelefonoProveedor(String telefonoProveedor)
/* 232:    */   {
/* 233:359 */     this.telefonoProveedor = telefonoProveedor;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public List<Documento> getListaDocumentoRecepcion()
/* 237:    */   {
/* 238:368 */     if (this.listaDocumentoRecepcion == null) {
/* 239:    */       try
/* 240:    */       {
/* 241:370 */         this.listaDocumentoRecepcion = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.RECEPCION_BODEGA);
/* 242:    */       }
/* 243:    */       catch (ExcepcionAS2 e)
/* 244:    */       {
/* 245:372 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 246:    */       }
/* 247:    */     }
/* 248:375 */     return this.listaDocumentoRecepcion;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setListaDocumentoRecepcion(List<Documento> listaDocumentoRecepcion)
/* 252:    */   {
/* 253:385 */     this.listaDocumentoRecepcion = listaDocumentoRecepcion;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String getNumero()
/* 257:    */   {
/* 258:394 */     return this.numero;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setNumero(String numero)
/* 262:    */   {
/* 263:404 */     this.numero = numero;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public String getIdentificacionProveedor()
/* 267:    */   {
/* 268:413 */     return this.identificacionProveedor;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setIdentificacionProveedor(String identificacionProveedor)
/* 272:    */   {
/* 273:423 */     this.identificacionProveedor = identificacionProveedor;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public CuentaContable getCuentaContable()
/* 277:    */   {
/* 278:432 */     return this.cuentaContable;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 282:    */   {
/* 283:442 */     this.cuentaContable = cuentaContable;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public DataTable getDtCuentaContable()
/* 287:    */   {
/* 288:451 */     return this.dtCuentaContable;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 292:    */   {
/* 293:461 */     this.dtCuentaContable = dtCuentaContable;
/* 294:    */   }
/* 295:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.FacturaProveedorAgilBean
 * JD-Core Version:    0.7.0.1
 */