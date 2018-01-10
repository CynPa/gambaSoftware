/*   1:    */ package com.asinfo.as2.finaciero.activos.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   7:    */ import com.asinfo.as2.entities.Depreciacion;
/*   8:    */ import com.asinfo.as2.entities.DetalleDepreciacion;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.financiero.activos.procesos.servicio.ServicioDepreciacion;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import java.math.BigDecimal;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.annotation.PostConstruct;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ import org.primefaces.component.datatable.DataTable;
/*  29:    */ import org.primefaces.model.LazyDataModel;
/*  30:    */ import org.primefaces.model.SortOrder;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class RevalorizacionBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -7983122759603197390L;
/*  38:    */   @EJB
/*  39:    */   private ServicioDepreciacion servicioDepreciacion;
/*  40:    */   @EJB
/*  41:    */   private ServicioDocumento servicioDocumento;
/*  42:    */   private Depreciacion depreciacion;
/*  43:    */   private ActivoFijo activoFijo;
/*  44:    */   private LazyDataModel<Depreciacion> listaDepreciacion;
/*  45:    */   private List<Documento> listaDocumentoCombo;
/*  46:    */   private DataTable dtDepreciacion;
/*  47:    */   private DataTable dtDetalleDepreciacion;
/*  48:    */   
/*  49:    */   @PostConstruct
/*  50:    */   public void init()
/*  51:    */   {
/*  52: 87 */     this.listaDepreciacion = new LazyDataModel()
/*  53:    */     {
/*  54:    */       private static final long serialVersionUID = 1L;
/*  55:    */       
/*  56:    */       public List<Depreciacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  57:    */       {
/*  58: 94 */         List<Depreciacion> lista = new ArrayList();
/*  59: 95 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  60:    */         
/*  61: 97 */         lista = RevalorizacionBean.this.servicioDepreciacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  62:    */         
/*  63: 99 */         RevalorizacionBean.this.listaDepreciacion.setRowCount(RevalorizacionBean.this.servicioDepreciacion.contarPorCriterio(filters));
/*  64:    */         
/*  65:101 */         return lista;
/*  66:    */       }
/*  67:    */     };
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void crearDepreciacion()
/*  71:    */   {
/*  72:115 */     this.depreciacion = new Depreciacion();
/*  73:116 */     this.depreciacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  74:117 */     this.depreciacion.setIdSucursal(AppUtil.getSucursal().getId());
/*  75:118 */     this.depreciacion.setActivoFijo(new ActivoFijo());
/*  76:119 */     this.depreciacion.setValorActivo(BigDecimal.ZERO);
/*  77:120 */     this.depreciacion.setValorDepreciado(BigDecimal.ZERO);
/*  78:121 */     this.depreciacion.setValorResidual(BigDecimal.ZERO);
/*  79:122 */     this.depreciacion.setValorADepreciar(BigDecimal.ZERO);
/*  80:123 */     this.depreciacion.setDocumentoRevalorizacion(new Documento());
/*  81:124 */     this.depreciacion.setEstado(Estado.ELABORADO);
/*  82:125 */     this.depreciacion.setActivo(true);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String editar()
/*  86:    */   {
/*  87:134 */     if (getDepreciacion().getIdDepreciacion() > 0) {
/*  88:    */       try
/*  89:    */       {
/*  90:136 */         this.servicioDepreciacion.esEditable(this.depreciacion);
/*  91:137 */         this.depreciacion = this.servicioDepreciacion.cargarDetalle(getDepreciacion().getId());
/*  92:138 */         setEditado(true);
/*  93:    */       }
/*  94:    */       catch (ExcepcionAS2Financiero e)
/*  95:    */       {
/*  96:140 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  97:    */       }
/*  98:    */     } else {
/*  99:143 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 100:    */     }
/* 101:145 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String guardar()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:155 */       this.servicioDepreciacion.guardar(this.depreciacion);
/* 109:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 110:157 */       setEditado(false);
/* 111:158 */       limpiar();
/* 112:    */     }
/* 113:    */     catch (ExcepcionAS2Financiero e)
/* 114:    */     {
/* 115:160 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getCodigoExcepcion());
/* 116:161 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 117:    */     }
/* 118:    */     catch (ExcepcionAS2 e)
/* 119:    */     {
/* 120:163 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getCodigoExcepcion());
/* 121:164 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 122:    */     }
/* 123:    */     catch (Exception e)
/* 124:    */     {
/* 125:166 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 126:167 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 127:    */     }
/* 128:169 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String eliminar()
/* 132:    */   {
/* 133:    */     try
/* 134:    */     {
/* 135:179 */       this.servicioDepreciacion.eliminar(this.depreciacion);
/* 136:180 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 137:    */     }
/* 138:    */     catch (Exception e)
/* 139:    */     {
/* 140:182 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 141:183 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 142:    */     }
/* 143:185 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String cargarDatos()
/* 147:    */   {
/* 148:194 */     return "";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String limpiar()
/* 152:    */   {
/* 153:203 */     crearDepreciacion();
/* 154:204 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List<DetalleDepreciacion> getListaDetalleDepreciacion()
/* 158:    */   {
/* 159:213 */     List<DetalleDepreciacion> lista = new ArrayList();
/* 160:214 */     for (DetalleDepreciacion detalleDepreciacion : getDepreciacion().getListaDetalleDepreciacion()) {
/* 161:215 */       if (!detalleDepreciacion.isEliminado()) {
/* 162:216 */         lista.add(detalleDepreciacion);
/* 163:    */       }
/* 164:    */     }
/* 165:219 */     return lista;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String cargarActivoFijo()
/* 169:    */   {
/* 170:228 */     this.depreciacion.setActivoFijo(this.activoFijo);
/* 171:229 */     calcularValorADepreciar();
/* 172:230 */     return "";
/* 173:    */   }
/* 174:    */   
/* 175:    */   public BigDecimal calcularValorADepreciar()
/* 176:    */   {
/* 177:239 */     BigDecimal valorDepreciar = BigDecimal.ZERO;
/* 178:240 */     valorDepreciar = this.depreciacion.getValorActivo().subtract(this.depreciacion.getValorDepreciado()).subtract(this.depreciacion.getValorResidual());
/* 179:241 */     return FuncionesUtiles.redondearBigDecimal(valorDepreciar);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String generarListaDepreciacion()
/* 183:    */   {
/* 184:    */     try
/* 185:    */     {
/* 186:255 */       this.servicioDepreciacion.generarListaRevalorizacion(this.depreciacion);
/* 187:    */     }
/* 188:    */     catch (ExcepcionAS2Financiero e)
/* 189:    */     {
/* 190:257 */       addInfoMessage("El anio o el mes no pueden ser igual o menores a la fecha de registros ya depreciados");
/* 191:    */     }
/* 192:260 */     return "";
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void actualizaAValorDepreciar()
/* 196:    */   {
/* 197:270 */     this.depreciacion.setValorADepreciar(calcularValorADepreciar());
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void actualizarDocumento()
/* 201:    */   {
/* 202:277 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.depreciacion.getDocumentoRevalorizacion().getId()));
/* 203:278 */     this.depreciacion.setDocumentoRevalorizacion(documento);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Depreciacion getDepreciacion()
/* 207:    */   {
/* 208:290 */     return this.depreciacion;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setDepreciacion(Depreciacion depreciacion)
/* 212:    */   {
/* 213:300 */     this.depreciacion = depreciacion;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public ActivoFijo getActivoFijo()
/* 217:    */   {
/* 218:309 */     return this.activoFijo;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 222:    */   {
/* 223:319 */     this.activoFijo = activoFijo;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public LazyDataModel<Depreciacion> getListaDepreciacion()
/* 227:    */   {
/* 228:328 */     return this.listaDepreciacion;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setListaDepreciacion(LazyDataModel<Depreciacion> listaDepreciacion)
/* 232:    */   {
/* 233:338 */     this.listaDepreciacion = listaDepreciacion;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public List<Documento> getListaDocumentoCombo()
/* 237:    */   {
/* 238:347 */     if (this.listaDocumentoCombo == null) {
/* 239:    */       try
/* 240:    */       {
/* 241:349 */         this.listaDocumentoCombo = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.REVALORIZACION);
/* 242:    */       }
/* 243:    */       catch (ExcepcionAS2 e)
/* 244:    */       {
/* 245:351 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 246:    */       }
/* 247:    */     }
/* 248:354 */     return this.listaDocumentoCombo;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setListaDocumentoCombo(List<Documento> listaDocumentoCombo)
/* 252:    */   {
/* 253:364 */     this.listaDocumentoCombo = listaDocumentoCombo;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public DataTable getDtDepreciacion()
/* 257:    */   {
/* 258:373 */     return this.dtDepreciacion;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setDtDepreciacion(DataTable dtDepreciacion)
/* 262:    */   {
/* 263:383 */     this.dtDepreciacion = dtDepreciacion;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public DataTable getDtDetalleDepreciacion()
/* 267:    */   {
/* 268:392 */     return this.dtDetalleDepreciacion;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setDtDetalleDepreciacion(DataTable dtDetalleDepreciacion)
/* 272:    */   {
/* 273:402 */     this.dtDetalleDepreciacion = dtDetalleDepreciacion;
/* 274:    */   }
/* 275:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.procesos.RevalorizacionBean
 * JD-Core Version:    0.7.0.1
 */