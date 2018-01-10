/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.PedidoCliente;
/*   7:    */ import com.asinfo.as2.entities.Secuencia;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  14:    */ import java.io.BufferedInputStream;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.io.InputStream;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Collection;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.List;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import javax.faces.context.FacesContext;
/*  26:    */ import javax.faces.context.PartialViewContext;
/*  27:    */ import javax.faces.model.SelectItem;
/*  28:    */ import org.primefaces.context.RequestContext;
/*  29:    */ import org.primefaces.event.FileUploadEvent;
/*  30:    */ import org.primefaces.model.UploadedFile;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class CargaPedidoClienteBean
/*  35:    */   extends PedidoClienteBean
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -2279483837568577014L;
/*  38:    */   @EJB
/*  39:    */   private ServicioPedidoCliente servicioPedidoCliente;
/*  40:    */   private TipoDocumentoEnum tipoDocumentoSeleccionado;
/*  41:    */   
/*  42:    */   private static enum TipoDocumentoEnum
/*  43:    */   {
/*  44: 62 */     EXCEL_SUPERMAXI,  CSV_SANTA_MARIA;
/*  45:    */     
/*  46:    */     private TipoDocumentoEnum() {}
/*  47:    */   }
/*  48:    */   
/*  49: 68 */   private List<ErrorCarga> errores = new ArrayList();
/*  50:    */   private List<PedidoCliente> listaPedidoClienteGuardar;
/*  51:    */   private boolean listo;
/*  52:    */   private PedidoCliente pedidoClienteGuardar;
/*  53:    */   
/*  54:    */   @PostConstruct
/*  55:    */   public void init()
/*  56:    */   {
/*  57: 78 */     this.tipoDocumentoSeleccionado = TipoDocumentoEnum.EXCEL_SUPERMAXI;
/*  58: 79 */     PedidoCliente pedidoCliente = new PedidoCliente();
/*  59: 80 */     setPedidoCliente(pedidoCliente);
/*  60: 81 */     pedidoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  61: 82 */     pedidoCliente.setSucursal(AppUtil.getSucursal());
/*  62: 83 */     pedidoCliente.setBodega(AppUtil.getBodega());
/*  63: 84 */     pedidoCliente.setNumero("");
/*  64: 85 */     pedidoCliente.setFecha(new Date());
/*  65: 86 */     pedidoCliente.setEstado(Estado.PROCESADO);
/*  66:    */     
/*  67: 88 */     Documento documento = null;
/*  68: 89 */     if ((getListaDocumentoCliente() != null) && (!getListaDocumentoCliente().isEmpty()))
/*  69:    */     {
/*  70: 90 */       documento = (Documento)getListaDocumentoCliente().get(0);
/*  71: 91 */       pedidoCliente.setDocumento(documento);
/*  72: 92 */       actualizarDocumento();
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76: 94 */       documento = new Documento();
/*  77: 95 */       documento.setSecuencia(new Secuencia());
/*  78: 96 */       pedidoCliente.setDocumento(documento);
/*  79:    */     }
/*  80: 99 */     setListaDireccionEmpresa(new ArrayList());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String migrarPedidoCliente(FileUploadEvent event)
/*  84:    */   {
/*  85:107 */     this.errores = new ArrayList();
/*  86:108 */     this.listaPedidoClienteGuardar = new ArrayList();
/*  87:    */     try
/*  88:    */     {
/*  89:111 */       String fileName = "migracion_pedido_cliente" + event.getFile().getFileName();
/*  90:112 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  91:113 */       Object[] resultado = null;
/*  92:114 */       if ((this.tipoDocumentoSeleccionado != null) && (this.tipoDocumentoSeleccionado.equals(TipoDocumentoEnum.EXCEL_SUPERMAXI))) {
/*  93:115 */         resultado = this.servicioPedidoCliente.cargarPedidosClienteExcelSupermaxi(getPedidoCliente(), fileName, input, 2);
/*  94:    */       }
/*  95:117 */       if ((this.tipoDocumentoSeleccionado != null) && (this.tipoDocumentoSeleccionado.equals(TipoDocumentoEnum.CSV_SANTA_MARIA))) {
/*  96:118 */         resultado = this.servicioPedidoCliente.cargarPedidosClienteCSVSantaMaria(getPedidoCliente(), input);
/*  97:    */       }
/*  98:120 */       this.listaPedidoClienteGuardar = ((List)resultado[0]);
/*  99:121 */       List<AS2Exception> listaErrores = (List)resultado[1];
/* 100:122 */       for (AS2Exception e : listaErrores) {
/* 101:123 */         cargarErrores(e);
/* 102:    */       }
/* 103:125 */       if (listaErrores.size() > 0)
/* 104:    */       {
/* 105:126 */         FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 106:127 */         RequestContext.getCurrentInstance().execute("dlgErrores.show()");
/* 107:    */       }
/* 108:    */     }
/* 109:    */     catch (AS2Exception e)
/* 110:    */     {
/* 111:130 */       cargarErrores(e);
/* 112:131 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 113:132 */       RequestContext.getCurrentInstance().execute("dlgErrores.show()");
/* 114:    */     }
/* 115:    */     catch (IOException e)
/* 116:    */     {
/* 117:135 */       e.printStackTrace();
/* 118:    */     }
/* 119:137 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   private void cargarErrores(AS2Exception e)
/* 123:    */   {
/* 124:141 */     List<String> listaMensajes = e.getCodigoMensajes();
/* 125:142 */     int i = 0;
/* 126:143 */     for (String a : listaMensajes)
/* 127:    */     {
/* 128:144 */       i = a.indexOf("*");
/* 129:145 */       a.substring(0, i + 1);
/* 130:146 */       ErrorCarga ec = new ErrorCarga();
/* 131:147 */       ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/* 132:148 */       this.errores.add(ec);
/* 133:    */     }
/* 134:150 */     for (String a : e.getMensajes())
/* 135:    */     {
/* 136:151 */       ErrorCarga ec = new ErrorCarga();
/* 137:152 */       ec.setError(a);
/* 138:153 */       this.errores.add(ec);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public List<ErrorCarga> getErrores()
/* 143:    */   {
/* 144:161 */     return this.errores;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setErrores(List<ErrorCarga> errores)
/* 148:    */   {
/* 149:165 */     this.errores = errores;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public TipoDocumentoEnum getTipoDocumentoSeleccionado()
/* 153:    */   {
/* 154:169 */     return this.tipoDocumentoSeleccionado;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setTipoDocumentoSeleccionado(TipoDocumentoEnum tipoDocumentoSeleccionado)
/* 158:    */   {
/* 159:173 */     this.tipoDocumentoSeleccionado = tipoDocumentoSeleccionado;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<SelectItem> getListaTipoDocumentoEnum()
/* 163:    */   {
/* 164:177 */     List<SelectItem> lista = new ArrayList();
/* 165:    */     
/* 166:    */ 
/* 167:180 */     TipoDocumentoEnum[] arrayTipoDocumento = TipoDocumentoEnum.values();
/* 168:182 */     for (TipoDocumentoEnum t : arrayTipoDocumento)
/* 169:    */     {
/* 170:183 */       SelectItem item = new SelectItem(t, t.toString());
/* 171:184 */       lista.add(item);
/* 172:    */     }
/* 173:186 */     return lista;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getAllowTypes()
/* 177:    */   {
/* 178:191 */     if ((this.tipoDocumentoSeleccionado != null) && (this.tipoDocumentoSeleccionado.equals(TipoDocumentoEnum.EXCEL_SUPERMAXI))) {
/* 179:192 */       return "/(\\.|\\/)(xls)$/";
/* 180:    */     }
/* 181:194 */     if ((this.tipoDocumentoSeleccionado != null) && (this.tipoDocumentoSeleccionado.equals(TipoDocumentoEnum.CSV_SANTA_MARIA))) {
/* 182:195 */       return "/(\\.|\\/)(csv)$/";
/* 183:    */     }
/* 184:197 */     return "";
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean isListo()
/* 188:    */   {
/* 189:201 */     if ((this.tipoDocumentoSeleccionado != null) && (getPedidoCliente() != null) && (getPedidoCliente().getEmpresa() != null) && 
/* 190:202 */       (getPedidoCliente().getSucursal() != null) && (getPedidoCliente().getDocumento() != null) && (getPedidoCliente().getFecha() != null) && 
/* 191:203 */       ((getPedidoCliente().getDireccionEmpresa() != null) || (this.tipoDocumentoSeleccionado.equals(TipoDocumentoEnum.CSV_SANTA_MARIA))) && 
/* 192:204 */       (getPedidoCliente().getCondicionPago() != null) && (getPedidoCliente().getNumeroCuotas() > 0) && 
/* 193:205 */       (getPedidoCliente().getTransportista() != null) && (getPedidoCliente().getBodega() != null)) {
/* 194:206 */       this.listo = true;
/* 195:    */     } else {
/* 196:208 */       this.listo = false;
/* 197:    */     }
/* 198:210 */     return this.listo;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setListo(boolean listo)
/* 202:    */   {
/* 203:214 */     this.listo = listo;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<PedidoCliente> getListaPedidoClienteGuardar()
/* 207:    */   {
/* 208:218 */     if (this.listaPedidoClienteGuardar == null) {
/* 209:219 */       this.listaPedidoClienteGuardar = new ArrayList();
/* 210:    */     }
/* 211:221 */     return this.listaPedidoClienteGuardar;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setListaPedidoClienteGuardar(List<PedidoCliente> listaPedidoClienteGuardar)
/* 215:    */   {
/* 216:225 */     this.listaPedidoClienteGuardar = listaPedidoClienteGuardar;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public PedidoCliente getPedidoClienteGuardar()
/* 220:    */   {
/* 221:229 */     return this.pedidoClienteGuardar;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setPedidoClienteGuardar(PedidoCliente pedidoClienteGuardar)
/* 225:    */   {
/* 226:233 */     this.pedidoClienteGuardar = pedidoClienteGuardar;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void guardarPedidoCliente()
/* 230:    */   {
/* 231:    */     try
/* 232:    */     {
/* 233:238 */       this.servicioPedidoCliente.guardar(this.pedidoClienteGuardar);
/* 234:239 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 235:    */     }
/* 236:    */     catch (ExcepcionAS2Financiero e)
/* 237:    */     {
/* 238:241 */       e.printStackTrace();
/* 239:242 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 240:    */     }
/* 241:    */     catch (ExcepcionAS2 e)
/* 242:    */     {
/* 243:244 */       e.printStackTrace();
/* 244:245 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void guardarPedidoClienteTodos()
/* 249:    */   {
/* 250:250 */     for (PedidoCliente pc : this.listaPedidoClienteGuardar) {
/* 251:251 */       if (pc.getId() == 0) {
/* 252:    */         try
/* 253:    */         {
/* 254:253 */           this.servicioPedidoCliente.guardar(pc);
/* 255:254 */           addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 256:    */         }
/* 257:    */         catch (ExcepcionAS2Financiero e)
/* 258:    */         {
/* 259:256 */           e.printStackTrace();
/* 260:257 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 261:    */         }
/* 262:    */         catch (ExcepcionAS2 e)
/* 263:    */         {
/* 264:259 */           e.printStackTrace();
/* 265:260 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 266:    */         }
/* 267:    */       }
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public boolean isHayPedidosSinGuardar()
/* 272:    */   {
/* 273:267 */     boolean hay = false;
/* 274:268 */     for (PedidoCliente pc : this.listaPedidoClienteGuardar) {
/* 275:269 */       if (pc.getId() == 0)
/* 276:    */       {
/* 277:270 */         hay = true;
/* 278:271 */         break;
/* 279:    */       }
/* 280:    */     }
/* 281:274 */     return hay;
/* 282:    */   }
/* 283:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.CargaPedidoClienteBean
 * JD-Core Version:    0.7.0.1
 */