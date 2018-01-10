/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.dao.DetallesFacturaContratoVentaDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*   8:    */ import com.asinfo.as2.entities.DetallesFacturaContratoVenta;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.FormaPagoSRI;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.DatosSRI;
/*  21:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDetalleFacturaContratoVenta;
/*  22:    */ import java.text.SimpleDateFormat;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.Iterator;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import javax.annotation.PostConstruct;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import javax.faces.model.SelectItem;
/*  34:    */ import org.primefaces.component.datatable.DataTable;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class FacturarContratoVentaBean
/*  39:    */   extends PageControllerAS2
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 1L;
/*  42:    */   @EJB
/*  43:    */   private ServicioDetalleFacturaContratoVenta servicioDetalleFacturaContratoVenta;
/*  44:    */   @EJB
/*  45:    */   private DetallesFacturaContratoVentaDao detallesFacturaContratoVentaDao;
/*  46:    */   @EJB
/*  47:    */   private ServicioUsuario servicioUsuario;
/*  48:    */   @EJB
/*  49:    */   private ServicioDocumento servicioDocumento;
/*  50:    */   @EJB
/*  51:    */   private ServicioFormaPagoSRI servicioFormaPagoSRI;
/*  52:    */   private List<DetallesFacturaContratoVenta> listaDetallesFacturaContratoVenta;
/*  53:    */   private List<DetallesFacturaContratoVenta> listaDetallesFacturaContratoVentaSeleccionados;
/*  54:    */   private List<DetallesFacturaContratoVenta> listaDetallesFacturaContratoVentaFiltrado;
/*  55:    */   private DataTable dtDetallesFacturaContratoVenta;
/*  56:    */   private DetallesFacturaContratoVenta detallesFacturaContratoVenta;
/*  57: 69 */   private boolean indicadorFacturado = false;
/*  58:    */   private List<EntidadUsuario> listaAgenteComercialCombo;
/*  59: 72 */   private Date fecha = new Date();
/*  60: 73 */   private Date fechaFacturacion = new Date();
/*  61: 75 */   private boolean indicadorAgenteComercial = false;
/*  62:    */   protected List<Documento> listaDocumento;
/*  63:    */   protected Documento documento;
/*  64: 82 */   private List<SelectItem> listaFormaPagoSRI = new ArrayList();
/*  65:    */   
/*  66:    */   public String editar()
/*  67:    */   {
/*  68: 87 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   @PostConstruct
/*  72:    */   public void init()
/*  73:    */   {
/*  74: 92 */     if (getListaDocumento().size() > 0) {
/*  75: 93 */       this.documento = ((Documento)getListaDocumento().get(0));
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String guardar()
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:100 */       this.servicioDetalleFacturaContratoVenta.crearFacturaCliente(this.listaDetallesFacturaContratoVentaSeleccionados, getFechaFacturacion(), AppUtil.getPuntoDeVenta(), AppUtil.getSucursal(), AppUtil.getOrganizacion(), this.documento);
/*  84:101 */       setListaDetallesFacturaContratoVenta(null);
/*  85:102 */       this.listaDetallesFacturaContratoVentaFiltrado = null;
/*  86:103 */       this.dtDetallesFacturaContratoVenta.reset();
/*  87:104 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  88:    */     }
/*  89:    */     catch (ExcepcionAS2 e)
/*  90:    */     {
/*  91:106 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  92:    */     }
/*  93:109 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String eliminar()
/*  97:    */   {
/*  98:114 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String limpiar()
/* 102:    */   {
/* 103:119 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String cargarDatos()
/* 107:    */   {
/* 108:124 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<DetallesFacturaContratoVenta> getListaDetallesFacturaContratoVenta()
/* 112:    */   {
/* 113:132 */     if (this.listaDetallesFacturaContratoVenta == null)
/* 114:    */     {
/* 115:133 */       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
/* 116:134 */       Map<String, String> filters = new HashMap();
/* 117:135 */       filters.put("indicadorFacturado", "false");
/* 118:136 */       filters.put("fecha", "<=" + dateFormat.format(getFecha()));
/* 119:137 */       filters.put("contratoVenta.estado", "!=" + Estado.ANULADO.toString());
/* 120:138 */       filters.put("contratoVenta.indicadorCerrado", "!=true");
/* 121:139 */       this.listaDetallesFacturaContratoVenta = this.servicioDetalleFacturaContratoVenta.obtenerListaPorPagina(0, 9999, "fecha", true, filters);
/* 122:    */     }
/* 123:141 */     return this.listaDetallesFacturaContratoVenta;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setListaDetallesFacturaContratoVenta(List<DetallesFacturaContratoVenta> listaDetallesFacturaContratoVenta)
/* 127:    */   {
/* 128:150 */     this.listaDetallesFacturaContratoVenta = listaDetallesFacturaContratoVenta;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void crearDetalleFacturaContratoVenta()
/* 132:    */   {
/* 133:157 */     this.detallesFacturaContratoVenta = new DetallesFacturaContratoVenta();
/* 134:158 */     this.detallesFacturaContratoVenta.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 135:159 */     this.detallesFacturaContratoVenta.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 136:    */   }
/* 137:    */   
/* 138:    */   public DataTable getDtDetallesFacturaContratoVenta()
/* 139:    */   {
/* 140:167 */     return this.dtDetallesFacturaContratoVenta;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setDtDetallesFacturaContratoVenta(DataTable dtDetallesFacturaContratoVenta)
/* 144:    */   {
/* 145:176 */     this.dtDetallesFacturaContratoVenta = dtDetallesFacturaContratoVenta;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setDetallesFacturaContratoVenta(DetallesFacturaContratoVenta detallesFacturaContratoVenta)
/* 149:    */   {
/* 150:185 */     this.detallesFacturaContratoVenta = detallesFacturaContratoVenta;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean isIndicadorFacturado()
/* 154:    */   {
/* 155:193 */     return this.indicadorFacturado;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setIndicadorFacturado(boolean indicadorFacturado)
/* 159:    */   {
/* 160:201 */     this.indicadorFacturado = indicadorFacturado;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Date getFecha()
/* 164:    */   {
/* 165:224 */     return this.fecha;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setFecha(Date fecha)
/* 169:    */   {
/* 170:232 */     this.fecha = fecha;
/* 171:233 */     this.listaDetallesFacturaContratoVenta = null;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Date getFechaFacturacion()
/* 175:    */   {
/* 176:237 */     return this.fechaFacturacion;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setFechaFacturacion(Date fechaFacturacion)
/* 180:    */   {
/* 181:241 */     this.fechaFacturacion = fechaFacturacion;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<EntidadUsuario> getListaAgenteComercialCombo()
/* 185:    */   {
/* 186:245 */     if (this.listaAgenteComercialCombo == null)
/* 187:    */     {
/* 188:246 */       this.listaAgenteComercialCombo = new ArrayList();
/* 189:247 */       this.listaAgenteComercialCombo = this.servicioUsuario.getEntidadUsuario(AppUtil.getOrganizacion().getId(), true, AppUtil.getSucursal(), Boolean.valueOf(true));
/* 190:    */     }
/* 191:250 */     return this.listaAgenteComercialCombo;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean isIndicadorAgenteComercial()
/* 195:    */   {
/* 196:254 */     return this.indicadorAgenteComercial;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setIndicadorAgenteComercial(boolean indicadorAgenteComercial)
/* 200:    */   {
/* 201:258 */     this.indicadorAgenteComercial = indicadorAgenteComercial;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public List<Documento> getListaDocumento()
/* 205:    */   {
/* 206:262 */     if (this.listaDocumento == null) {
/* 207:    */       try
/* 208:    */       {
/* 209:264 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.FACTURA_CLIENTE, AppUtil.getOrganizacion().getId());
/* 210:    */       }
/* 211:    */       catch (ExcepcionAS2 e)
/* 212:    */       {
/* 213:266 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 214:    */       }
/* 215:    */     }
/* 216:269 */     return this.listaDocumento;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public Documento getDocumento()
/* 220:    */   {
/* 221:273 */     return this.documento;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setDocumento(Documento documento)
/* 225:    */   {
/* 226:277 */     this.documento = documento;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public List<DetallesFacturaContratoVenta> getListaDetallesFacturaContratoVentaSeleccionados()
/* 230:    */   {
/* 231:281 */     return this.listaDetallesFacturaContratoVentaSeleccionados;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setListaDetallesFacturaContratoVentaSeleccionados(List<DetallesFacturaContratoVenta> listaDetallesFacturaContratoVentaSeleccionados)
/* 235:    */   {
/* 236:286 */     this.listaDetallesFacturaContratoVentaSeleccionados = listaDetallesFacturaContratoVentaSeleccionados;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<DetallesFacturaContratoVenta> getListaDetallesFacturaContratoVentaFiltrado()
/* 240:    */   {
/* 241:290 */     return this.listaDetallesFacturaContratoVentaFiltrado;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setListaDetallesFacturaContratoVentaFiltrado(List<DetallesFacturaContratoVenta> listaDetallesFacturaContratoVentaFiltrado)
/* 245:    */   {
/* 246:294 */     this.listaDetallesFacturaContratoVentaFiltrado = listaDetallesFacturaContratoVentaFiltrado;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public List<SelectItem> cargaFormaPagoSRI(Empresa empresa)
/* 250:    */   {
/* 251:298 */     this.listaFormaPagoSRI = new ArrayList();
/* 252:300 */     for (Iterator localIterator1 = this.servicioFormaPagoSRI.getListaFormaPagoSRI(empresa).iterator(); localIterator1.hasNext();)
/* 253:    */     {
/* 254:300 */       formaPagoSRI = (FormaPagoSRI)localIterator1.next();
/* 255:301 */       for (SelectItem seleItem : DatosSRI.getListaFormaPago(AppUtil.getOrganizacion().getId())) {
/* 256:302 */         if (formaPagoSRI.getCodigo().equals(seleItem.getValue().toString())) {
/* 257:303 */           this.listaFormaPagoSRI.add(seleItem);
/* 258:    */         }
/* 259:    */       }
/* 260:    */     }
/* 261:    */     FormaPagoSRI formaPagoSRI;
/* 262:308 */     if (this.listaFormaPagoSRI.size() == 0) {
/* 263:309 */       this.listaFormaPagoSRI.addAll(DatosSRI.getListaFormaPago(AppUtil.getOrganizacion().getId()));
/* 264:    */     }
/* 265:313 */     return this.listaFormaPagoSRI;
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.FacturarContratoVentaBean
 * JD-Core Version:    0.7.0.1
 */