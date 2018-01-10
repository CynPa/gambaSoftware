/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.LlamadoAtencion;
/*  11:    */ import com.asinfo.as2.entities.MotivoLlamadoAtencion;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioLlamadoAtencion;
/*  17:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioMotivoLlamadoAtencion;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.util.RutaArchivo;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import java.io.BufferedInputStream;
/*  22:    */ import java.io.File;
/*  23:    */ import java.io.FileOutputStream;
/*  24:    */ import java.io.InputStream;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.HashMap;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import javax.annotation.PostConstruct;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ import org.primefaces.event.FileUploadEvent;
/*  35:    */ import org.primefaces.model.LazyDataModel;
/*  36:    */ import org.primefaces.model.SortOrder;
/*  37:    */ import org.primefaces.model.StreamedContent;
/*  38:    */ import org.primefaces.model.UploadedFile;
/*  39:    */ 
/*  40:    */ @ManagedBean
/*  41:    */ @ViewScoped
/*  42:    */ public class LlamadoAtencionBean
/*  43:    */   extends PageControllerAS2
/*  44:    */ {
/*  45:    */   private static final long serialVersionUID = 1L;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioLlamadoAtencion servicioLlamadoAtencion;
/*  48:    */   @EJB
/*  49:    */   private ServicioMotivoLlamadoAtencion servicioMotivoLlamadoAtencion;
/*  50:    */   @EJB
/*  51:    */   private ServicioEmpresa servicioEmpresa;
/*  52:    */   @EJB
/*  53:    */   private ServicioDocumento servicioDocumento;
/*  54:    */   private LlamadoAtencion llamadoAtencion;
/*  55:    */   private List<MotivoLlamadoAtencion> listaMotivoLlamadoAtencion;
/*  56:    */   private LlamadoAtencion selectedLlamadoAtencion;
/*  57:    */   private Empleado empleado;
/*  58:    */   private boolean editarEmpleado;
/*  59:    */   private String allowTypes;
/*  60:    */   private String sizeLimit;
/*  61:    */   private StreamedContent file;
/*  62:    */   private StreamedContent fileLlamadoAtencion;
/*  63:    */   private LazyDataModel<LlamadoAtencion> listaLlamadoAtencion;
/*  64:    */   
/*  65:    */   @PostConstruct
/*  66:    */   public void init()
/*  67:    */   {
/*  68:100 */     this.listaLlamadoAtencion = new LazyDataModel()
/*  69:    */     {
/*  70:    */       private static final long serialVersionUID = 1L;
/*  71:    */       
/*  72:    */       public List<LlamadoAtencion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  73:    */       {
/*  74:107 */         List<LlamadoAtencion> lista = new ArrayList();
/*  75:108 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  76:109 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  77:110 */         lista = LlamadoAtencionBean.this.servicioLlamadoAtencion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  78:111 */         LlamadoAtencionBean.this.listaLlamadoAtencion.setRowCount(LlamadoAtencionBean.this.servicioLlamadoAtencion.contarPorCriterio(filters));
/*  79:112 */         return lista;
/*  80:    */       }
/*  81:    */     };
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void processUpload(FileUploadEvent eventDocumento)
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:123 */       String fileName = "emp_" + AppUtil.getOrganizacion().getId() + "_" + getLlamadoAtencion().getEmpleado().getEmpresa().getCodigo() + "_" + eventDocumento.getFile().getFileName();
/*  89:    */       
/*  90:125 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "llamados_atencion");
/*  91:    */       
/*  92:127 */       File directorio = new File(uploadDir);
/*  93:    */       
/*  94:129 */       File file = new File(uploadDir + fileName);
/*  95:131 */       if (!directorio.exists()) {
/*  96:132 */         directorio.mkdirs();
/*  97:    */       }
/*  98:135 */       InputStream input = new BufferedInputStream(eventDocumento.getFile().getInputstream());
/*  99:136 */       this.llamadoAtencion.setFichero(fileName);
/* 100:    */       
/* 101:138 */       FileOutputStream output = new FileOutputStream(file);
/* 102:140 */       while (input.available() != 0) {
/* 103:141 */         output.write(input.read());
/* 104:    */       }
/* 105:144 */       input.close();
/* 106:145 */       output.close();
/* 107:146 */       HashMap<String, Object> campos = new HashMap();
/* 108:    */       
/* 109:148 */       campos.put("fichero", fileName);
/* 110:149 */       this.servicioLlamadoAtencion.actualizarAtributoEntidad(this.llamadoAtencion, campos);
/* 111:    */       
/* 112:    */ 
/* 113:152 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:155 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_imagen"));
/* 118:156 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   private void crearLlamadoAtencion()
/* 123:    */   {
/* 124:166 */     this.llamadoAtencion = new LlamadoAtencion();
/* 125:167 */     this.llamadoAtencion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 126:168 */     this.llamadoAtencion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 127:169 */     this.llamadoAtencion.setActivo(true);
/* 128:170 */     this.llamadoAtencion.setDocumento(getDocumentoLlamadoAtencion());
/* 129:171 */     setEditarEmpleado(false);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String cargarEmpleado()
/* 133:    */   {
/* 134:175 */     this.llamadoAtencion.setEmpleado(getEmpleado());
/* 135:176 */     return "";
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String editar()
/* 139:    */   {
/* 140:185 */     if ((getLlamadoAtencion() == null) || (getLlamadoAtencion().getId() == 0))
/* 141:    */     {
/* 142:186 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 143:    */     }
/* 144:    */     else
/* 145:    */     {
/* 146:188 */       setEditarEmpleado(true);
/* 147:189 */       setEditado(true);
/* 148:    */     }
/* 149:191 */     return "";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String guardar()
/* 153:    */   {
/* 154:    */     try
/* 155:    */     {
/* 156:201 */       this.servicioLlamadoAtencion.guardar(this.llamadoAtencion);
/* 157:202 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 158:203 */       setEditado(false);
/* 159:204 */       limpiar();
/* 160:    */     }
/* 161:    */     catch (Exception e)
/* 162:    */     {
/* 163:206 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 164:207 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 165:    */     }
/* 166:209 */     return "";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String cargarDatos()
/* 170:    */   {
/* 171:218 */     return "";
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String limpiar()
/* 175:    */   {
/* 176:223 */     crearLlamadoAtencion();
/* 177:224 */     return null;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String eliminar()
/* 181:    */   {
/* 182:    */     try
/* 183:    */     {
/* 184:234 */       this.servicioLlamadoAtencion.eliminar(getLlamadoAtencion());
/* 185:235 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 186:    */     }
/* 187:    */     catch (Exception e)
/* 188:    */     {
/* 189:237 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 190:238 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 191:    */     }
/* 192:240 */     return "";
/* 193:    */   }
/* 194:    */   
/* 195:    */   public LlamadoAtencion getLlamadoAtencion()
/* 196:    */   {
/* 197:244 */     return this.llamadoAtencion;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setLlamadoAtencion(LlamadoAtencion llamadoAtencion)
/* 201:    */   {
/* 202:248 */     this.llamadoAtencion = llamadoAtencion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void asignarFileLlamadoAtencion(LlamadoAtencion llamadoAtencionSelect)
/* 206:    */   {
/* 207:    */     try
/* 208:    */     {
/* 209:253 */       String fileName = llamadoAtencionSelect.getFichero();
/* 210:    */       
/* 211:255 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "llamados_atencion");
/* 212:256 */       setFileLlamadoAtencion(FuncionesUtiles.descargarArchivo(uploadDir + fileName, "", fileName));
/* 213:    */     }
/* 214:    */     catch (Exception e)
/* 215:    */     {
/* 216:258 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 217:259 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<MotivoLlamadoAtencion> getListaMotivoLlamadoAtencion()
/* 222:    */   {
/* 223:264 */     HashMap<String, String> filters = new HashMap();
/* 224:265 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 225:266 */     filters.put("activo", "true");
/* 226:267 */     this.listaMotivoLlamadoAtencion = this.servicioMotivoLlamadoAtencion.obtenerListaCombo("nombre", true, filters);
/* 227:268 */     return this.listaMotivoLlamadoAtencion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public Documento getDocumentoLlamadoAtencion()
/* 231:    */   {
/* 232:    */     try
/* 233:    */     {
/* 234:274 */       return (Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.LLAMADO_ATENCION, AppUtil.getOrganizacion().getIdOrganizacion()).get(0);
/* 235:    */     }
/* 236:    */     catch (ExcepcionAS2 e)
/* 237:    */     {
/* 238:277 */       e.printStackTrace();
/* 239:    */     }
/* 240:278 */     return null;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setListaMotivoLlamadoAtencion(List<MotivoLlamadoAtencion> listaMotivoLlamadoAtencion)
/* 244:    */   {
/* 245:283 */     this.listaMotivoLlamadoAtencion = listaMotivoLlamadoAtencion;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public LlamadoAtencion getSelectedLlamadoAtencion()
/* 249:    */   {
/* 250:287 */     return this.selectedLlamadoAtencion;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setSelectedLlamadoAtencion(LlamadoAtencion selectedLlamadoAtencion)
/* 254:    */   {
/* 255:291 */     this.selectedLlamadoAtencion = selectedLlamadoAtencion;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String getAllowTypes()
/* 259:    */   {
/* 260:295 */     this.allowTypes = "/(\\.|\\/)(gif|jpe?g|png|pdf|tif|zip|rar)$/";
/* 261:296 */     return this.allowTypes;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setAllowTypes(String allowTypes)
/* 265:    */   {
/* 266:300 */     this.allowTypes = allowTypes;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getSizeLimit()
/* 270:    */   {
/* 271:304 */     this.sizeLimit = "5000000";
/* 272:305 */     return this.sizeLimit;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setSizeLimit(String sizeLimit)
/* 276:    */   {
/* 277:309 */     this.sizeLimit = sizeLimit;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public StreamedContent getFile()
/* 281:    */   {
/* 282:313 */     return this.file;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setFile(StreamedContent file)
/* 286:    */   {
/* 287:318 */     this.file = file;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public StreamedContent getFileLlamadoAtencion()
/* 291:    */   {
/* 292:323 */     return this.fileLlamadoAtencion;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setFileLlamadoAtencion(StreamedContent fileLlamadoAtencion)
/* 296:    */   {
/* 297:328 */     this.fileLlamadoAtencion = fileLlamadoAtencion;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public LazyDataModel<LlamadoAtencion> getListaLlamadoAtencion()
/* 301:    */   {
/* 302:333 */     return this.listaLlamadoAtencion;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setListaLlamadoAtencion(LazyDataModel<LlamadoAtencion> listaLlamadoAtencion)
/* 306:    */   {
/* 307:338 */     this.listaLlamadoAtencion = listaLlamadoAtencion;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public Empleado getEmpleado()
/* 311:    */   {
/* 312:342 */     return this.empleado;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setEmpleado(Empleado empleado)
/* 316:    */   {
/* 317:347 */     this.empleado = empleado;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public boolean isEditarEmpleado()
/* 321:    */   {
/* 322:355 */     return this.editarEmpleado;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setEditarEmpleado(boolean editarEmpleado)
/* 326:    */   {
/* 327:363 */     this.editarEmpleado = editarEmpleado;
/* 328:    */   }
/* 329:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.LlamadoAtencionBean
 * JD-Core Version:    0.7.0.1
 */