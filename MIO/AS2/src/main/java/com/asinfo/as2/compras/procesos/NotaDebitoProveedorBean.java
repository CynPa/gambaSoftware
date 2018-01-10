/*   1:    */ package com.asinfo.as2.compras.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioNotaDebitoProveedor;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Proveedor;
/*  15:    */ import com.asinfo.as2.entities.Secuencia;
/*  16:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  17:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ import org.primefaces.event.SelectEvent;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class NotaDebitoProveedorBean
/*  37:    */   extends FacturaProveedorBaseBean
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 375482513203239242L;
/*  40:    */   @EJB
/*  41:    */   private transient ServicioNotaDebitoProveedor servicioNotaDebitoProveedor;
/*  42:    */   
/*  43:    */   public void obtenerFiltros(Map<String, String> filters)
/*  44:    */   {
/*  45: 64 */     filters.put("documento.documentoBase", DocumentoBase.NOTA_DEBITO_PROVEEDOR.toString());
/*  46: 65 */     filters.put("documento.indicadorDocumentoExterior", "false");
/*  47:    */   }
/*  48:    */   
/*  49:    */   @PostConstruct
/*  50:    */   public void init()
/*  51:    */   {
/*  52: 76 */     super.init();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<Documento> getListaDocumentoFactura()
/*  56:    */   {
/*  57:    */     try
/*  58:    */     {
/*  59: 87 */       if (this.listaDocumentoFactura == null)
/*  60:    */       {
/*  61: 88 */         this.listaDocumentoFactura = new ArrayList();
/*  62: 90 */         for (Documento documento : this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.NOTA_DEBITO_PROVEEDOR)) {
/*  63: 91 */           if (!documento.isIndicadorDocumentoExterior()) {
/*  64: 92 */             this.listaDocumentoFactura.add(documento);
/*  65:    */           }
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:    */     catch (ExcepcionAS2 e)
/*  70:    */     {
/*  71: 97 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  72:    */     }
/*  73:100 */     return this.listaDocumentoFactura;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String guardar()
/*  77:    */   {
/*  78:    */     try
/*  79:    */     {
/*  80:111 */       this.servicioNotaDebitoProveedor.guardar(this.facturaProveedor);
/*  81:    */       
/*  82:113 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  83:    */       
/*  84:115 */       limpiar();
/*  85:    */     }
/*  86:    */     catch (ExcepcionAS2Financiero e)
/*  87:    */     {
/*  88:118 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  89:119 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  90:    */     }
/*  91:    */     catch (ExcepcionAS2Compras e)
/*  92:    */     {
/*  93:123 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  94:124 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  95:125 */       e.printStackTrace();
/*  96:    */     }
/*  97:    */     catch (ExcepcionAS2 e)
/*  98:    */     {
/*  99:128 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 100:129 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 101:    */     }
/* 102:    */     catch (Exception e)
/* 103:    */     {
/* 104:132 */       addInfoMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 105:133 */       e.printStackTrace();
/* 106:    */     }
/* 107:135 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void totalizar()
/* 111:    */   {
/* 112:    */     try
/* 113:    */     {
/* 114:148 */       for (DetalleFacturaProveedor dfp : getListaDetalleFacturaProveedorServicios()) {
/* 115:149 */         if ((dfp.getListaGastoProductoFactura().isEmpty()) && 
/* 116:150 */           (dfp.getBaseImponible().add(dfp.getValorImpuestosLinea()).compareTo(BigDecimal.ZERO) > 0)) {
/* 117:151 */           agregarGastoProductoFacturaProveedor(dfp);
/* 118:    */         }
/* 119:    */       }
/* 120:155 */       this.servicioNotaDebitoProveedor.totalizar(getFacturaProveedor());
/* 121:    */       
/* 122:    */ 
/* 123:158 */       cargarCuentaPorPagar();
/* 124:    */     }
/* 125:    */     catch (ExcepcionAS2Compras e)
/* 126:    */     {
/* 127:161 */       LOG.info(e.getErrorMessage(e));
/* 128:    */     }
/* 129:    */     catch (Exception e)
/* 130:    */     {
/* 131:163 */       LOG.info(e);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String limpiar()
/* 136:    */   {
/* 137:174 */     setEditado(false);
/* 138:    */     
/* 139:176 */     crearNotaDebitoProveedor();
/* 140:177 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void crearNotaDebitoProveedor()
/* 144:    */   {
/* 145:184 */     super.setCentroCosto(null);
/* 146:    */     
/* 147:186 */     this.facturaProveedor = new FacturaProveedor();
/* 148:    */     
/* 149:188 */     this.facturaProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 150:189 */     this.facturaProveedor.setSucursal(AppUtil.getSucursal());
/* 151:190 */     this.facturaProveedor.setNumero("");
/* 152:191 */     this.facturaProveedor.setFecha(new Date());
/* 153:192 */     this.facturaProveedor.setEstado(Estado.APROBADO);
/* 154:193 */     this.facturaProveedor.setNumeroCuotas(1);
/* 155:194 */     this.facturaProveedor.setFacturaProveedorSRI(null);
/* 156:    */     
/* 157:196 */     Documento documento = null;
/* 158:197 */     if ((getListaDocumentoFactura() != null) && (!getListaDocumentoFactura().isEmpty()))
/* 159:    */     {
/* 160:198 */       documento = (Documento)getListaDocumentoFactura().get(0);
/* 161:199 */       this.facturaProveedor.setDocumento(documento);
/* 162:200 */       actualizarDocumento();
/* 163:    */     }
/* 164:    */     else
/* 165:    */     {
/* 166:202 */       documento = new Documento();
/* 167:203 */       documento.setSecuencia(new Secuencia());
/* 168:204 */       this.facturaProveedor.setDocumento(documento);
/* 169:    */     }
/* 170:207 */     super.setListaDireccionEmpresa(new ArrayList());
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String actualizarDocumento()
/* 174:    */   {
/* 175:217 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.facturaProveedor.getDocumento().getId()));
/* 176:218 */     this.facturaProveedor.setDocumento(documento);
/* 177:    */     try
/* 178:    */     {
/* 179:229 */       this.servicioFacturaProveedor.cargarSecuencia(this.facturaProveedor, null);
/* 180:    */     }
/* 181:    */     catch (ExcepcionAS2 e)
/* 182:    */     {
/* 183:232 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 184:    */     }
/* 185:235 */     setSecuenciaEditable(!this.facturaProveedor.getDocumento().isIndicadorBloqueoSecuencia());
/* 186:    */     
/* 187:237 */     return "";
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void cargarCuentaPorPagar()
/* 191:    */   {
/* 192:    */     try
/* 193:    */     {
/* 194:248 */       this.servicioNotaDebitoProveedor.generarCuentaPorPagar(this.facturaProveedor);
/* 195:    */     }
/* 196:    */     catch (ExcepcionAS2 e)
/* 197:    */     {
/* 198:250 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void actualizarProveedorListener(SelectEvent event)
/* 203:    */   {
/* 204:265 */     Empresa empresa = this.servicioEmpresa.obtenerDatosProveedor(((Empresa)event.getObject()).getIdEmpresa());
/* 205:266 */     actualizarProveedor(empresa);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void actualizarProveedor(Empresa empresa)
/* 209:    */   {
/* 210:277 */     getFacturaProveedor().setEmpresa(empresa);
/* 211:286 */     if (getFacturaProveedor().getCondicionPago() != null) {
/* 212:287 */       getFacturaProveedor().setCondicionPago(empresa.getProveedor().getCondicionPago());
/* 213:    */     }
/* 214:291 */     if (getFacturaProveedor().getNumeroCuotas() == 0) {
/* 215:292 */       getFacturaProveedor().setNumeroCuotas(empresa.getProveedor().getNumeroCuotas());
/* 216:    */     }
/* 217:295 */     cargarDirecciones();
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List<FacturaProveedor> autocompletarFacturas(String consulta)
/* 221:    */   {
/* 222:305 */     Map<String, String> filters = new HashMap();
/* 223:306 */     List<FacturaProveedor> lista = new ArrayList();
/* 224:308 */     if (this.facturaProveedor.getEmpresa() != null)
/* 225:    */     {
/* 226:309 */       filters.put("empresa.idEmpresa", "" + this.facturaProveedor.getEmpresa().getId());
/* 227:310 */       if ((consulta != null) && (!consulta.isEmpty())) {
/* 228:311 */         filters.put("numero", "%" + consulta);
/* 229:    */       }
/* 230:314 */       filters.put("documento.documentoBase", DocumentoBase.FACTURA_PROVEEDOR.toString());
/* 231:315 */       filters.put("estado", "!=" + Estado.ANULADO.toString());
/* 232:    */       
/* 233:317 */       lista = this.servicioFacturaProveedor.obtenerListaCombo("fecha", true, filters);
/* 234:    */     }
/* 235:    */     else
/* 236:    */     {
/* 237:319 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 238:    */     }
/* 239:322 */     return lista;
/* 240:    */   }
/* 241:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.NotaDebitoProveedorBean
 * JD-Core Version:    0.7.0.1
 */