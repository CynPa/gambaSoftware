/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Cobro;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  13:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import com.asinfo.as2.utils.JsfUtil;
/*  17:    */ import java.io.BufferedInputStream;
/*  18:    */ import java.io.InputStream;
/*  19:    */ import java.io.PrintStream;
/*  20:    */ import java.io.StringReader;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.jdom2.Document;
/*  30:    */ import org.jdom2.Element;
/*  31:    */ import org.jdom2.input.SAXBuilder;
/*  32:    */ import org.primefaces.event.FileUploadEvent;
/*  33:    */ import org.primefaces.model.UploadedFile;
/*  34:    */ import org.xml.sax.InputSource;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class CargaRetencionClienteMasivaBean
/*  39:    */   extends PageControllerAS2
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 1L;
/*  42:    */   @EJB
/*  43:    */   private ServicioDocumento servicioDocumento;
/*  44:    */   @EJB
/*  45:    */   private ServicioCobro servicioCobro;
/*  46:    */   private List<Documento> listaDocumento;
/*  47: 61 */   private List<Object[]> listaResumenFacturas = new ArrayList();
/*  48: 62 */   private List<Cobro> listaCobros = new ArrayList();
/*  49: 63 */   HashMap<String, Cobro> hmCobro = new HashMap();
/*  50:    */   private int numeroArchivos;
/*  51:    */   private int numeroArchivosConProblemas;
/*  52:    */   private int numeroArchivosProcesados;
/*  53:    */   private Date fecha;
/*  54:    */   
/*  55:    */   public String editar()
/*  56:    */   {
/*  57: 77 */     return "";
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void procesarCobros()
/*  61:    */   {
/*  62: 81 */     for (Cobro c : getListaCobros())
/*  63:    */     {
/*  64: 83 */       String numeroFactura = "";
/*  65: 84 */       System.out.println("cobro ---->>>   " + c);
/*  66: 85 */       if ((c.getNumeroFactura() != null) && (!c.getNumeroFactura().isEmpty())) {
/*  67: 86 */         numeroFactura = c.getNumeroFactura();
/*  68:    */       }
/*  69: 89 */       Object[] objetAux = null;
/*  70: 90 */       for (Object[] observaciones : getListaResumenFacturas()) {
/*  71: 91 */         if (observaciones[0].toString().equals(numeroFactura)) {
/*  72: 92 */           objetAux = observaciones;
/*  73:    */         }
/*  74:    */       }
/*  75:    */       try
/*  76:    */       {
/*  77: 97 */         if (c.getListaDetalleCobro().size() > 0)
/*  78:    */         {
/*  79: 98 */           c.setFecha(getFecha());
/*  80: 99 */           this.servicioCobro.guardar(c);
/*  81:100 */           addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  82:101 */           this.numeroArchivosProcesados += 1;
/*  83:102 */           if (objetAux != null) {
/*  84:103 */             objetAux[3] = "SIN PROBLEMAS";
/*  85:    */           }
/*  86:    */         }
/*  87:    */         else
/*  88:    */         {
/*  89:106 */           objetAux[3] = "Factura Cancelada o No Existe";
/*  90:    */         }
/*  91:    */       }
/*  92:    */       catch (ExcepcionAS2Financiero e)
/*  93:    */       {
/*  94:110 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  95:111 */         if (objetAux != null) {
/*  96:112 */           objetAux[3] = (getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  97:    */         }
/*  98:115 */         e.printStackTrace();
/*  99:    */       }
/* 100:    */       catch (ExcepcionAS2 e)
/* 101:    */       {
/* 102:117 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 103:118 */         if (objetAux != null) {
/* 104:119 */           objetAux[3] = (getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 105:    */         }
/* 106:122 */         e.printStackTrace();
/* 107:    */       }
/* 108:    */       catch (AS2Exception e)
/* 109:    */       {
/* 110:124 */         if (objetAux != null) {
/* 111:125 */           objetAux[3] = (getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 112:    */         }
/* 113:128 */         JsfUtil.addErrorMessage(e, "");
/* 114:    */       }
/* 115:    */       catch (Exception e)
/* 116:    */       {
/* 117:130 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 118:131 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/* 119:132 */         if (objetAux != null) {
/* 120:133 */           objetAux[3] = getLanguageController().getMensaje("msg_error_guardar");
/* 121:    */         }
/* 122:136 */         e.printStackTrace();
/* 123:    */       }
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String guardar()
/* 128:    */   {
/* 129:148 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String cargarXMLMasivo(FileUploadEvent event)
/* 133:    */   {
/* 134:153 */     this.numeroArchivos += 1;
/* 135:    */     try
/* 136:    */     {
/* 137:156 */       Documento documento = null;
/* 138:157 */       if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 139:    */       {
/* 140:158 */         documento = this.servicioDocumento.buscarPorId(Integer.valueOf(((Documento)getListaDocumento().get(0)).getIdDocumento()));
/* 141:159 */         setSecuenciaEditable(!documento.isIndicadorBloqueoSecuencia());
/* 142:    */       }
/* 143:161 */       documento = (Documento)getListaDocumento().get(0);
/* 144:162 */       Documento documentoAux = this.servicioDocumento.buscarPorId(Integer.valueOf(documento.getIdDocumento()));
/* 145:    */       
/* 146:164 */       setSecuenciaEditable(!documento.isIndicadorBloqueoSecuencia());
/* 147:    */       
/* 148:166 */       SAXBuilder builder = new SAXBuilder();
/* 149:    */       
/* 150:168 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 151:    */       
/* 152:    */ 
/* 153:171 */       Document document = builder.build(input);
/* 154:172 */       Element rootNode = document.getRootElement();
/* 155:173 */       Element nodoComprobante = rootNode.getChild("comprobante");
/* 156:    */       
/* 157:175 */       String comprobante = nodoComprobante.getText();
/* 158:176 */       comprobante = FuncionesUtiles.quitarCDATA(comprobante);
/* 159:    */       
/* 160:178 */       Document documentString = builder.build(new InputSource(new StringReader(comprobante)));
/* 161:179 */       Element rootNodes = documentString.getRootElement();
/* 162:180 */       Element nodoInfoTributaria = rootNodes.getChild("infoTributaria");
/* 163:181 */       String claveAcceso = nodoInfoTributaria.getChildText("claveAcceso");
/* 164:    */       
/* 165:183 */       List<Element> infoImpuestos = rootNodes.getChildren("impuestos");
/* 166:185 */       if (!getHmCobro().containsKey(claveAcceso))
/* 167:    */       {
/* 168:186 */         getHmCobro().put(claveAcceso, new Cobro());
/* 169:187 */         List<Cobro> listaCobro = this.servicioCobro.cargarXML(getListaResumenFacturas(), infoImpuestos, nodoInfoTributaria, 
/* 170:188 */           AppUtil.getOrganizacion().getIdOrganizacion(), documentoAux, AppUtil.getSucursal(), AppUtil.getCaja(), event
/* 171:189 */           .getFile().getFileName(), false);
/* 172:190 */         getListaCobros().addAll(listaCobro);
/* 173:    */       }
/* 174:    */     }
/* 175:    */     catch (AS2Exception e)
/* 176:    */     {
/* 177:194 */       this.numeroArchivosConProblemas += 1;
/* 178:195 */       JsfUtil.addErrorMessage(e, "");
/* 179:196 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 180:197 */       e.printStackTrace();
/* 181:    */       try
/* 182:    */       {
/* 183:199 */         Thread.sleep(3000L);
/* 184:    */       }
/* 185:    */       catch (InterruptedException localInterruptedException) {}
/* 186:    */     }
/* 187:    */     catch (ExcepcionAS2 e)
/* 188:    */     {
/* 189:204 */       this.numeroArchivosConProblemas += 1;
/* 190:205 */       e.printStackTrace();
/* 191:    */     }
/* 192:    */     catch (Exception e)
/* 193:    */     {
/* 194:207 */       this.numeroArchivosConProblemas += 1;
/* 195:208 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 196:209 */       e.printStackTrace();
/* 197:    */       try
/* 198:    */       {
/* 199:211 */         Thread.sleep(5000L);
/* 200:    */       }
/* 201:    */       catch (InterruptedException localInterruptedException1) {}
/* 202:    */     }
/* 203:216 */     return null;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String eliminar()
/* 207:    */   {
/* 208:226 */     return "";
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String limpiar()
/* 212:    */   {
/* 213:236 */     return "";
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String cargarDatos()
/* 217:    */   {
/* 218:246 */     return "";
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String crearAnticipoCliente()
/* 222:    */   {
/* 223:251 */     return "";
/* 224:    */   }
/* 225:    */   
/* 226:    */   public List<Documento> getListaDocumento()
/* 227:    */   {
/* 228:255 */     if (this.listaDocumento == null) {
/* 229:    */       try
/* 230:    */       {
/* 231:257 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.COBRO_CLIENTE, AppUtil.getOrganizacion()
/* 232:258 */           .getIdOrganizacion());
/* 233:    */       }
/* 234:    */       catch (ExcepcionAS2 e)
/* 235:    */       {
/* 236:260 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 237:    */       }
/* 238:    */     }
/* 239:263 */     return this.listaDocumento;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 243:    */   {
/* 244:267 */     this.listaDocumento = listaDocumento;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<Object[]> getListaResumenFacturas()
/* 248:    */   {
/* 249:271 */     if (this.listaResumenFacturas == null) {
/* 250:272 */       this.listaResumenFacturas = new ArrayList();
/* 251:    */     }
/* 252:274 */     return this.listaResumenFacturas;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setListaResumenFacturas(List<Object[]> listaResumenFacturas)
/* 256:    */   {
/* 257:278 */     this.listaResumenFacturas = listaResumenFacturas;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public List<Cobro> getListaCobros()
/* 261:    */   {
/* 262:282 */     return this.listaCobros;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setListaCobros(List<Cobro> listaCobros)
/* 266:    */   {
/* 267:286 */     this.listaCobros = listaCobros;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public HashMap<String, Cobro> getHmCobro()
/* 271:    */   {
/* 272:290 */     if (this.hmCobro == null) {
/* 273:291 */       this.hmCobro = new HashMap();
/* 274:    */     }
/* 275:293 */     return this.hmCobro;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setHmCobro(HashMap<String, Cobro> hmCobro)
/* 279:    */   {
/* 280:297 */     this.hmCobro = hmCobro;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public int getNumeroArchivos()
/* 284:    */   {
/* 285:301 */     return this.numeroArchivos;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setNumeroArchivos(int numeroArchivos)
/* 289:    */   {
/* 290:305 */     this.numeroArchivos = numeroArchivos;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public int getNumeroArchivosConProblemas()
/* 294:    */   {
/* 295:309 */     return this.numeroArchivosConProblemas;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setNumeroArchivosConProblemas(int numeroArchivosConProblemas)
/* 299:    */   {
/* 300:313 */     this.numeroArchivosConProblemas = numeroArchivosConProblemas;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public Date getFecha()
/* 304:    */   {
/* 305:317 */     return this.fecha == null ? new Date() : this.fecha;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setFecha(Date fecha)
/* 309:    */   {
/* 310:321 */     this.fecha = fecha;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public int getNumeroArchivosProcesados()
/* 314:    */   {
/* 315:325 */     return this.numeroArchivosProcesados;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setNumeroArchivosProcesados(int numeroArchivosProcesados)
/* 319:    */   {
/* 320:329 */     this.numeroArchivosProcesados = numeroArchivosProcesados;
/* 321:    */   }
/* 322:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.CargaRetencionClienteMasivaBean
 * JD-Core Version:    0.7.0.1
 */