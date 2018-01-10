/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   6:    */ import com.asinfo.as2.entities.CategoriaDocumentoDigitalizado;
/*   7:    */ import com.asinfo.as2.entities.Departamento;
/*   8:    */ import com.asinfo.as2.entities.DetalleDocumentoDigitalizado;
/*   9:    */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*  10:    */ import com.asinfo.as2.entities.Empleado;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCategoriaDocumentoDigitalizado;
/*  13:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDetalleDocumentoDigitalizado;
/*  14:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDocumentoDigitalizado;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.util.RutaArchivo;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import javax.faces.model.SelectItem;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ import org.primefaces.component.datatable.DataTable;
/*  29:    */ import org.primefaces.model.LazyDataModel;
/*  30:    */ import org.primefaces.model.SortOrder;
/*  31:    */ import org.primefaces.model.StreamedContent;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @ViewScoped
/*  35:    */ public class DescargaDocumentosDigitalizadosBean
/*  36:    */   extends PageControllerAS2
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @EJB
/*  40:    */   private ServicioDepartamento servicioDepartamento;
/*  41:    */   @EJB
/*  42:    */   private ServicioCategoriaDocumentoDigitalizado servicioCategoriaDocumentoDigitalizado;
/*  43:    */   @EJB
/*  44:    */   private ServicioDocumentoDigitalizado servicioDocumentoDigitalizado;
/*  45:    */   @EJB
/*  46:    */   private ServicioDetalleDocumentoDigitalizado servicioDetalleDocumentoDigitalizado;
/*  47:    */   private Empleado empleado;
/*  48:    */   private Departamento departamento;
/*  49:    */   private List<SelectItem> listaDepartamento;
/*  50:    */   private List<SelectItem> listaCategoriaDocumento;
/*  51:    */   private List<SelectItem> listaDocumentoDigitalizado;
/*  52:    */   private DetalleDocumentoDigitalizado detalleDocumentoDigitalizado;
/*  53:    */   private StreamedContent file;
/*  54:    */   private int idCategoriaSeleccionada;
/*  55:    */   private int idDocumentoDigitalizado;
/*  56:    */   private int tipoReporte;
/*  57:    */   LazyDataModel<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizado;
/*  58:    */   private DataTable dtDetalleDocumentoDigitalizado;
/*  59:    */   
/*  60:    */   @PostConstruct
/*  61:    */   public void init()
/*  62:    */   {
/*  63: 88 */     this.listaDetalleDocumentoDigitalizado = new LazyDataModel()
/*  64:    */     {
/*  65:    */       private static final long serialVersionUID = 1L;
/*  66:    */       
/*  67:    */       public List<DetalleDocumentoDigitalizado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  68:    */       {
/*  69: 97 */         List<DetalleDocumentoDigitalizado> lista = new ArrayList();
/*  70: 98 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  71: 99 */         lista = DescargaDocumentosDigitalizadosBean.this.servicioDetalleDocumentoDigitalizado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  72:100 */         DescargaDocumentosDigitalizadosBean.this.listaDetalleDocumentoDigitalizado.setRowCount(DescargaDocumentosDigitalizadosBean.this.servicioDetalleDocumentoDigitalizado.contarPorCriterio(filters));
/*  73:    */         
/*  74:102 */         return lista;
/*  75:    */       }
/*  76:    */     };
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String cargarEmpleado()
/*  80:    */   {
/*  81:109 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Empleado getEmpleado()
/*  85:    */   {
/*  86:118 */     if (this.empleado == null) {
/*  87:119 */       this.empleado = new Empleado();
/*  88:    */     }
/*  89:121 */     return this.empleado;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setEmpleado(Empleado empleado)
/*  93:    */   {
/*  94:131 */     this.empleado = empleado;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Departamento getDepartamento()
/*  98:    */   {
/*  99:152 */     if (this.departamento == null) {
/* 100:153 */       this.departamento = new Departamento();
/* 101:    */     }
/* 102:155 */     return this.departamento;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setDepartamento(Departamento departamento)
/* 106:    */   {
/* 107:165 */     this.departamento = departamento;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<SelectItem> getListaDepartamento()
/* 111:    */   {
/* 112:174 */     if (this.listaDepartamento == null)
/* 113:    */     {
/* 114:175 */       this.listaDepartamento = new ArrayList();
/* 115:176 */       this.listaDepartamento.add(new SelectItem("%%", ""));
/* 116:177 */       Map<String, String> filters = new HashMap();
/* 117:178 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 118:179 */       List<Departamento> lista = this.servicioDepartamento.obtenerListaCombo("nombre", true, filters);
/* 119:180 */       for (Departamento itemDepartamento : lista) {
/* 120:181 */         this.listaDepartamento.add(new SelectItem(itemDepartamento.getNombre(), itemDepartamento.getNombre()));
/* 121:    */       }
/* 122:    */     }
/* 123:184 */     return this.listaDepartamento;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<SelectItem> getListaCategoriaDocumento()
/* 127:    */   {
/* 128:193 */     if (this.listaCategoriaDocumento == null)
/* 129:    */     {
/* 130:194 */       this.listaCategoriaDocumento = new ArrayList();
/* 131:195 */       this.listaCategoriaDocumento.add(new SelectItem("%%", ""));
/* 132:196 */       Map<String, String> filters = new HashMap();
/* 133:197 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 134:198 */       List<CategoriaDocumentoDigitalizado> lista = this.servicioCategoriaDocumentoDigitalizado.obtenerListaCombo("nombre", true, filters);
/* 135:199 */       for (CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado : lista) {
/* 136:200 */         this.listaCategoriaDocumento.add(new SelectItem(categoriaDocumentoDigitalizado.getNombre(), categoriaDocumentoDigitalizado.getNombre()));
/* 137:    */       }
/* 138:    */     }
/* 139:203 */     return this.listaCategoriaDocumento;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getIdCategoriaSeleccionada()
/* 143:    */   {
/* 144:207 */     return this.idCategoriaSeleccionada;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setIdCategoriaSeleccionada(int idCategoriaSeleccionada)
/* 148:    */   {
/* 149:211 */     Map<String, String> filters = new HashMap();
/* 150:212 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 151:213 */     filters.put("categoriaDocumentoDigitalizado.idCategoriaDocumentoDigitalizado", Integer.toString(idCategoriaSeleccionada));
/* 152:214 */     List<DocumentoDigitalizado> listaDocumentos = this.servicioDocumentoDigitalizado.obtenerListaCombo("nombre", true, filters);
/* 153:215 */     this.listaDocumentoDigitalizado = new ArrayList();
/* 154:216 */     for (DocumentoDigitalizado documentoDigitalizado : listaDocumentos) {
/* 155:217 */       this.listaDocumentoDigitalizado.add(new SelectItem(Integer.valueOf(documentoDigitalizado.getId()), documentoDigitalizado.getNombre()));
/* 156:    */     }
/* 157:219 */     this.idCategoriaSeleccionada = idCategoriaSeleccionada;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getIdDocumentoDigitalizado()
/* 161:    */   {
/* 162:223 */     return this.idDocumentoDigitalizado;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setIdDocumentoDigitalizado(int idDocumentoDigitalizado)
/* 166:    */   {
/* 167:227 */     this.idDocumentoDigitalizado = idDocumentoDigitalizado;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<SelectItem> getListaDocumentoDigitalizado()
/* 171:    */   {
/* 172:231 */     Map<String, String> filters = new HashMap();
/* 173:232 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 174:233 */     List<DocumentoDigitalizado> listaDocumentos = this.servicioDocumentoDigitalizado.obtenerListaCombo("nombre", true, filters);
/* 175:234 */     this.listaDocumentoDigitalizado = new ArrayList();
/* 176:235 */     for (DocumentoDigitalizado documentoDigitalizado : listaDocumentos) {
/* 177:236 */       this.listaDocumentoDigitalizado.add(new SelectItem(Integer.valueOf(documentoDigitalizado.getId()), documentoDigitalizado.getCategoriaDocumentoDigitalizado().getNombre() + "/" + documentoDigitalizado.getNombre()));
/* 178:    */     }
/* 179:238 */     return this.listaDocumentoDigitalizado;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setListaDocumentoDigitalizado(List<SelectItem> listaDocumentoDigitalizado)
/* 183:    */   {
/* 184:243 */     this.listaDocumentoDigitalizado = listaDocumentoDigitalizado;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getTipoReporte()
/* 188:    */   {
/* 189:247 */     return this.tipoReporte;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setTipoReporte(int tipoReporte)
/* 193:    */   {
/* 194:251 */     this.tipoReporte = tipoReporte;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String editar()
/* 198:    */   {
/* 199:258 */     return null;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public String guardar()
/* 203:    */   {
/* 204:266 */     return null;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String eliminar()
/* 208:    */   {
/* 209:274 */     return null;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public String limpiar()
/* 213:    */   {
/* 214:282 */     return null;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String cargarDatos()
/* 218:    */   {
/* 219:290 */     return null;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public LazyDataModel<DetalleDocumentoDigitalizado> getListaDetalleDocumentoDigitalizado()
/* 223:    */   {
/* 224:294 */     return this.listaDetalleDocumentoDigitalizado;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setListaDetalleDocumentoDigitalizado(LazyDataModel<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizado)
/* 228:    */   {
/* 229:300 */     this.listaDetalleDocumentoDigitalizado = listaDetalleDocumentoDigitalizado;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public DataTable getDtDetalleDocumentoDigitalizado()
/* 233:    */   {
/* 234:305 */     return this.dtDetalleDocumentoDigitalizado;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setDtDetalleDocumentoDigitalizado(DataTable dtDetalleDocumentoDigitalizado)
/* 238:    */   {
/* 239:311 */     this.dtDetalleDocumentoDigitalizado = dtDetalleDocumentoDigitalizado;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public DetalleDocumentoDigitalizado getDetalleDocumentoDigitalizadoEmpleado()
/* 243:    */   {
/* 244:316 */     return this.detalleDocumentoDigitalizado;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setDetalleDocumentoDigitalizadoEmpleado(DetalleDocumentoDigitalizado detalleDocumentoDigitalizado)
/* 248:    */   {
/* 249:322 */     this.detalleDocumentoDigitalizado = detalleDocumentoDigitalizado;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public StreamedContent getFile()
/* 253:    */   {
/* 254:327 */     return this.file;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setFile(StreamedContent file)
/* 258:    */   {
/* 259:332 */     this.file = file;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void asignarFile(DetalleDocumentoDigitalizado detalleDoc)
/* 263:    */   {
/* 264:    */     try
/* 265:    */     {
/* 266:337 */       String fileName = detalleDoc.getFichero();
/* 267:    */       
/* 268:339 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "documentos_empleado");
/* 269:340 */       this.file = FuncionesUtiles.descargarArchivo(uploadDir + fileName, "", fileName);
/* 270:    */     }
/* 271:    */     catch (Exception e)
/* 272:    */     {
/* 273:342 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 274:    */       
/* 275:344 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 276:    */     }
/* 277:    */   }
/* 278:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.DescargaDocumentosDigitalizadosBean
 * JD-Core Version:    0.7.0.1
 */