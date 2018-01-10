/*   1:    */ package com.asinfo.as2.finaciero.SRI.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.ServicioComprobanteElectronicoPeriodico;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI;
/*   8:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   9:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.JsfUtil;
/*  14:    */ import java.text.SimpleDateFormat;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ import org.primefaces.event.SelectEvent;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ComprobanteElectronicoPendienteBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @EJB
/*  36:    */   private ServicioGenerico<ComprobanteElectronicoPendienteSRI> servicioComprobanteElectronicoPendienteSRI;
/*  37:    */   @EJB
/*  38:    */   private ServicioComprobanteElectronicoPeriodico servicioComprobanteElectronicoPeriodico;
/*  39:    */   private ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI;
/*  40:    */   private LazyDataModel<ComprobanteElectronicoPendienteSRI> listaComprobanteElectronicoPendienteSRI;
/*  41:    */   private DocumentoBase documentoBase;
/*  42:    */   private DataTable dataTableComprobanteElectronicoPendienteSRI;
/*  43:    */   private Date fechaDesde;
/*  44:    */   private Date fechaHasta;
/*  45:    */   private boolean renderDialog;
/*  46:    */   
/*  47:    */   @PostConstruct
/*  48:    */   public void init()
/*  49:    */   {
/*  50: 81 */     this.listaComprobanteElectronicoPendienteSRI = new LazyDataModel()
/*  51:    */     {
/*  52:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  53:    */       
/*  54:    */       public List<ComprobanteElectronicoPendienteSRI> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  55:    */       {
/*  56: 88 */         filters = ComprobanteElectronicoPendienteBean.this.agregarFiltroOrganizacion(filters);
/*  57: 89 */         List<ComprobanteElectronicoPendienteSRI> lista = new ArrayList();
/*  58: 90 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  59:    */         try
/*  60:    */         {
/*  61: 92 */           lista = ComprobanteElectronicoPendienteBean.this.servicioComprobanteElectronicoPendienteSRI.obtenerListaPorPagina(ComprobanteElectronicoPendienteSRI.class, startIndex, pageSize, sortField, ordenar, filters);
/*  62:    */         }
/*  63:    */         catch (Exception e)
/*  64:    */         {
/*  65: 95 */           e.printStackTrace();
/*  66:    */         }
/*  67: 97 */         ComprobanteElectronicoPendienteBean.this.listaComprobanteElectronicoPendienteSRI.setRowCount(ComprobanteElectronicoPendienteBean.this.servicioComprobanteElectronicoPendienteSRI.contarPorCriterio(ComprobanteElectronicoPendienteSRI.class, filters));
/*  68:    */         
/*  69: 99 */         return lista;
/*  70:    */       }
/*  71:    */     };
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String cargarDatos()
/*  75:    */   {
/*  76:111 */     setEditado(false);
/*  77:    */     try
/*  78:    */     {
/*  79:113 */       limpiar();
/*  80:    */     }
/*  81:    */     catch (Exception e)
/*  82:    */     {
/*  83:115 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  84:116 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  85:    */     }
/*  86:118 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String limpiar()
/*  90:    */   {
/*  91:128 */     this.comprobanteElectronicoPendienteSRI = new ComprobanteElectronicoPendienteSRI();
/*  92:129 */     this.comprobanteElectronicoPendienteSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  93:130 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void onRowSelect(SelectEvent event)
/*  97:    */   {
/*  98:138 */     ComprobanteElectronicoPendienteSRI cep = (ComprobanteElectronicoPendienteSRI)event.getObject();
/*  99:139 */     setComprobanteElectronicoPendienteSRI(cep);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public LazyDataModel<ComprobanteElectronicoPendienteSRI> getListaComprobanteElectronicoPendienteSRI()
/* 103:    */   {
/* 104:148 */     if (this.listaComprobanteElectronicoPendienteSRI == null) {
/* 105:149 */       cargarDatos();
/* 106:    */     }
/* 107:151 */     return this.listaComprobanteElectronicoPendienteSRI;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setListaComprobanteElectronicoPendienteSRI(LazyDataModel<ComprobanteElectronicoPendienteSRI> listaComprobanteElectronicoPendienteSRI)
/* 111:    */   {
/* 112:161 */     this.listaComprobanteElectronicoPendienteSRI = listaComprobanteElectronicoPendienteSRI;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public ComprobanteElectronicoPendienteSRI getComprobanteElectronicoPendienteSRI()
/* 116:    */   {
/* 117:165 */     return this.comprobanteElectronicoPendienteSRI;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setComprobanteElectronicoPendienteSRI(ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI)
/* 121:    */   {
/* 122:169 */     this.comprobanteElectronicoPendienteSRI = comprobanteElectronicoPendienteSRI;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public DataTable getDataTableComprobanteElectronicoPendienteSRI()
/* 126:    */   {
/* 127:173 */     return this.dataTableComprobanteElectronicoPendienteSRI;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setDataTableComprobanteElectronicoPendienteSRI(DataTable dataTableComprobanteElectronicoPendienteSRI)
/* 131:    */   {
/* 132:177 */     this.dataTableComprobanteElectronicoPendienteSRI = dataTableComprobanteElectronicoPendienteSRI;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String editar()
/* 136:    */   {
/* 137:182 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 138:183 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String guardar()
/* 142:    */   {
/* 143:188 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 144:189 */     return "";
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String eliminar()
/* 148:    */   {
/* 149:194 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 150:195 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String crear()
/* 154:    */   {
/* 155:200 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 156:201 */     return "";
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String procesarcomprobanteElectronicoPendienteSRIMasivo()
/* 160:    */   {
/* 161:205 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 162:206 */     SimpleDateFormat sdf2 = new SimpleDateFormat(getFormatoFecha());
/* 163:207 */     filters.put("fechaEmision", OperacionEnum.BETWEEN.name() + sdf2.format(this.fechaDesde) + "~" + sdf2.format(this.fechaHasta));
/* 164:208 */     filters.put("documentoBase", this.documentoBase.name());
/* 165:    */     
/* 166:210 */     List<ComprobanteElectronicoPendienteSRI> listCEP = this.servicioComprobanteElectronicoPendienteSRI.obtenerListaCombo(ComprobanteElectronicoPendienteSRI.class, "idComprobanteElectronicoPendienteSRI", true, filters);
/* 167:212 */     for (ComprobanteElectronicoPendienteSRI cep : listCEP) {
/* 168:213 */       procesar(cep);
/* 169:    */     }
/* 170:215 */     return null;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void procesar(ComprobanteElectronicoPendienteSRI cep)
/* 174:    */   {
/* 175:    */     try
/* 176:    */     {
/* 177:220 */       if ((cep.isIndicadorRechazado()) && (cep.getMensajeSRI().endsWith("CLAVE ACCESO REGISTRADA")))
/* 178:    */       {
/* 179:221 */         cep.setIndicadorRechazado(false);
/* 180:222 */         cep.setIndicadorNoEnviado(false);
/* 181:223 */         cep.setIndicadorComprobarAutorizacion(true);
/* 182:224 */         this.servicioComprobanteElectronicoPendienteSRI.guardar(cep);
/* 183:    */       }
/* 184:226 */       if (cep.isIndicadorRechazado())
/* 185:    */       {
/* 186:227 */         cep.setIndicadorRechazado(false);
/* 187:228 */         cep.setIndicadorNoEnviado(true);
/* 188:229 */         cep.setIndicadorComprobarAutorizacion(false);
/* 189:230 */         this.servicioComprobanteElectronicoPendienteSRI.guardar(cep);
/* 190:    */       }
/* 191:232 */       if (cep.isIndicadorComprobarAutorizacion()) {
/* 192:233 */         this.servicioComprobanteElectronicoPeriodico.comprobarAutorizacionComprobanteSRI(cep, true);
/* 193:    */       }
/* 194:235 */       if (cep.isIndicadorNoEnviado()) {
/* 195:236 */         this.servicioComprobanteElectronicoPeriodico.enviarComprobanteSRI(cep);
/* 196:    */       }
/* 197:    */     }
/* 198:    */     catch (AS2Exception e)
/* 199:    */     {
/* 200:239 */       JsfUtil.addErrorMessage(e, "");
/* 201:    */     }
/* 202:    */     catch (Exception e)
/* 203:    */     {
/* 204:241 */       e.printStackTrace();
/* 205:242 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 206:    */     }
/* 207:244 */     init();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Date getFechaDesde()
/* 211:    */   {
/* 212:248 */     return this.fechaDesde;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setFechaDesde(Date fechaDesde)
/* 216:    */   {
/* 217:252 */     this.fechaDesde = fechaDesde;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Date getFechaHasta()
/* 221:    */   {
/* 222:256 */     return this.fechaHasta;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setFechaHasta(Date fechaHasta)
/* 226:    */   {
/* 227:260 */     this.fechaHasta = fechaHasta;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<DocumentoBase> getListaDocumentosBase()
/* 231:    */   {
/* 232:264 */     List<DocumentoBase> list = new ArrayList();
/* 233:265 */     list.add(DocumentoBase.FACTURA_CLIENTE);
/* 234:266 */     list.add(DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 235:267 */     list.add(DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 236:268 */     list.add(DocumentoBase.RETENCION_PROVEEDOR);
/* 237:269 */     list.add(DocumentoBase.GUIA_REMISION);
/* 238:270 */     return list;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean isRenderDialog()
/* 242:    */   {
/* 243:274 */     return this.renderDialog;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setRenderDialog(boolean renderDialog)
/* 247:    */   {
/* 248:278 */     this.renderDialog = renderDialog;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public DocumentoBase getDocumentoBase()
/* 252:    */   {
/* 253:282 */     return this.documentoBase;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 257:    */   {
/* 258:286 */     this.documentoBase = documentoBase;
/* 259:    */   }
/* 260:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.ComprobanteElectronicoPendienteBean
 * JD-Core Version:    0.7.0.1
 */