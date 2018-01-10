/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioMotivoAnulacion;
/*  11:    */ import com.asinfo.as2.entities.Canal;
/*  12:    */ import com.asinfo.as2.entities.Ciudad;
/*  13:    */ import com.asinfo.as2.entities.CondicionPago;
/*  14:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  15:    */ import com.asinfo.as2.entities.Empresa;
/*  16:    */ import com.asinfo.as2.entities.MotivoAnulacion;
/*  17:    */ import com.asinfo.as2.entities.Organizacion;
/*  18:    */ import com.asinfo.as2.entities.Producto;
/*  19:    */ import com.asinfo.as2.entities.Zona;
/*  20:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  21:    */ import com.asinfo.as2.enumeraciones.Accion;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  23:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  25:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  26:    */ import com.asinfo.as2.util.AppUtil;
/*  27:    */ import com.asinfo.as2.util.SecurityUtil;
/*  28:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  29:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  30:    */ import java.math.BigDecimal;
/*  31:    */ import java.util.ArrayList;
/*  32:    */ import java.util.HashMap;
/*  33:    */ import java.util.List;
/*  34:    */ import java.util.Map;
/*  35:    */ import javax.ejb.EJB;
/*  36:    */ import javax.faces.bean.ManagedProperty;
/*  37:    */ import javax.security.auth.Subject;
/*  38:    */ 
/*  39:    */ public abstract class DocumentoBaseClienteBean
/*  40:    */   extends PageControllerAS2
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 7542258710818073090L;
/*  43:    */   @EJB
/*  44:    */   protected transient ServicioProducto servicioProducto;
/*  45:    */   @EJB
/*  46:    */   protected transient ServicioDocumento servicioDocumento;
/*  47:    */   @EJB
/*  48:    */   protected transient ServicioEmpresa servicioEmpresa;
/*  49:    */   @EJB
/*  50:    */   protected transient ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  51:    */   @EJB
/*  52:    */   protected transient ServicioListaPrecios servicioListaPrecios;
/*  53:    */   @EJB
/*  54:    */   protected transient ServicioListaDescuentos servicioListaDescuentos;
/*  55:    */   @EJB
/*  56:    */   protected transient ServicioCanal servicioCanal;
/*  57:    */   @EJB
/*  58:    */   protected transient ServicioUsuario servicioUsuario;
/*  59:    */   @EJB
/*  60:    */   protected transient ServicioCondicionPago servicioCondicionPago;
/*  61:    */   @EJB
/*  62:    */   protected transient ServicioZona servicioZona;
/*  63:    */   @EJB
/*  64:    */   protected transient ServicioMotivoAnulacion servicioMotivoAnulacion;
/*  65:    */   @EJB
/*  66:    */   protected transient ServicioCiudad servicioCiudad;
/*  67:    */   private List<Empresa> listaEmpresaCliente;
/*  68:    */   private List<CondicionPago> listaCondicionPago;
/*  69:    */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  70:    */   private List<Canal> listaCanal;
/*  71:    */   private List<Zona> listaZona;
/*  72:    */   private List<MotivoAnulacion> listaMotivoAnulacion;
/*  73:    */   private List<EntidadUsuario> listaAgenteComercialCombo;
/*  74:    */   private String usuarioAutorizaVenta;
/*  75:    */   private String claveAutorizaVenta;
/*  76:    */   private boolean mostrarAutorizarVenta;
/*  77:    */   @ManagedProperty("#{listaProductoBean}")
/*  78:    */   private ListaProductoBean listaProductoBean;
/*  79:    */   private BigDecimal descuentoGeneral;
/*  80:    */   
/*  81:    */   public List<Empresa> getListaEmpresaCliente()
/*  82:    */   {
/*  83:110 */     if (this.listaEmpresaCliente == null) {
/*  84:111 */       this.listaEmpresaCliente = this.servicioEmpresa.obtenerClientes();
/*  85:    */     }
/*  86:113 */     return this.listaEmpresaCliente;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setListaEmpresaCliente(List<Empresa> listaEmpresaCliente)
/*  90:    */   {
/*  91:123 */     this.listaEmpresaCliente = listaEmpresaCliente;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/*  95:    */   {
/*  96:132 */     if (this.listaDireccionEmpresa == null) {
/*  97:133 */       this.listaDireccionEmpresa = new ArrayList();
/*  98:    */     }
/*  99:135 */     return this.listaDireccionEmpresa;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 103:    */   {
/* 104:145 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List<EntidadUsuario> getListaAgenteComercialCombo()
/* 108:    */   {
/* 109:154 */     if (this.listaAgenteComercialCombo == null)
/* 110:    */     {
/* 111:155 */       this.listaAgenteComercialCombo = new ArrayList();
/* 112:156 */       this.listaAgenteComercialCombo = this.servicioUsuario.obtenerListaAgenteComercial(AppUtil.getOrganizacion().getId(), Boolean.valueOf(true));
/* 113:    */     }
/* 114:159 */     return this.listaAgenteComercialCombo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setListaAgenteComercialCombo(List<EntidadUsuario> listaAgenteComercialCombo)
/* 118:    */   {
/* 119:169 */     this.listaAgenteComercialCombo = listaAgenteComercialCombo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 123:    */   {
/* 124:173 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<Ciudad> autocompletarCiudad(String consulta)
/* 128:    */   {
/* 129:177 */     return this.servicioCiudad.autocompletarCiudad(consulta);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<Producto> autocompletarProductos(String consulta)
/* 133:    */   {
/* 134:181 */     Map<String, String> filtros = new HashMap();
/* 135:182 */     filtros.put("indicadorVenta", String.valueOf(true));
/* 136:183 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta, filtros);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public List<Canal> getListaCanal()
/* 140:    */   {
/* 141:192 */     if (this.listaCanal == null) {
/* 142:193 */       this.listaCanal = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 143:    */     }
/* 144:195 */     return this.listaCanal;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setListaCanal(List<Canal> listaCanal)
/* 148:    */   {
/* 149:205 */     this.listaCanal = listaCanal;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<Zona> getListaZona()
/* 153:    */   {
/* 154:214 */     if (this.listaZona == null) {
/* 155:215 */       this.listaZona = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 156:    */     }
/* 157:217 */     return this.listaZona;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaZona(List<Zona> listaZona)
/* 161:    */   {
/* 162:227 */     this.listaZona = listaZona;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<CondicionPago> getListaCondicionPago()
/* 166:    */   {
/* 167:234 */     if (this.listaCondicionPago == null) {
/* 168:235 */       this.listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("", false, null);
/* 169:    */     }
/* 170:237 */     return this.listaCondicionPago;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setListaCondicionPago(List<CondicionPago> listaCondicionPago)
/* 174:    */   {
/* 175:245 */     this.listaCondicionPago = listaCondicionPago;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public ListaProductoBean getListaProductoBean()
/* 179:    */   {
/* 180:252 */     return this.listaProductoBean;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 184:    */   {
/* 185:260 */     this.listaProductoBean = listaProductoBean;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<MotivoAnulacion> getListaMotivoAnulacion()
/* 189:    */   {
/* 190:264 */     if (this.listaMotivoAnulacion == null) {
/* 191:265 */       this.listaMotivoAnulacion = this.servicioMotivoAnulacion.obtenerListaCombo("nombre", true, null);
/* 192:    */     }
/* 193:267 */     return this.listaMotivoAnulacion;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public BigDecimal getDescuentoGeneral()
/* 197:    */   {
/* 198:271 */     return this.descuentoGeneral;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setDescuentoGeneral(BigDecimal descuentoGeneral)
/* 202:    */   {
/* 203:275 */     this.descuentoGeneral = descuentoGeneral;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String getUsuarioAutorizaVenta()
/* 207:    */   {
/* 208:279 */     return this.usuarioAutorizaVenta;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setUsuarioAutorizaVenta(String usuarioAutorizaVenta)
/* 212:    */   {
/* 213:283 */     this.usuarioAutorizaVenta = usuarioAutorizaVenta;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String getClaveAutorizaVenta()
/* 217:    */   {
/* 218:287 */     return this.claveAutorizaVenta;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setClaveAutorizaVenta(String claveAutorizaVenta)
/* 222:    */   {
/* 223:291 */     this.claveAutorizaVenta = claveAutorizaVenta;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean isMostrarAutorizarVenta()
/* 227:    */   {
/* 228:295 */     return this.mostrarAutorizarVenta;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setMostrarAutorizarVenta(boolean mostrarAutorizarVenta)
/* 232:    */   {
/* 233:299 */     this.mostrarAutorizarVenta = mostrarAutorizarVenta;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean isCrearClienteAgil()
/* 237:    */   {
/* 238:305 */     Subject subject = (Subject)AppUtil.getAtributo("javax.security.auth.subject");
/* 239:306 */     String viewId = "/paginas/datosBase/empresaAgil.xhtml";
/* 240:307 */     return SecurityUtil.hasPermissionToAccessViewId(subject, viewId, Accion.CREATE.toString());
/* 241:    */   }
/* 242:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.DocumentoBaseClienteBean
 * JD-Core Version:    0.7.0.1
 */