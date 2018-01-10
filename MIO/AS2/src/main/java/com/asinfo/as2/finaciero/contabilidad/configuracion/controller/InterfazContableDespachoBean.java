/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   7:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  14:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  17:    */ import java.math.BigDecimal;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Calendar;
/*  20:    */ import java.util.Collection;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.annotation.PostConstruct;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import javax.faces.context.FacesContext;
/*  28:    */ import javax.faces.context.PartialViewContext;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ import org.primefaces.component.datatable.DataTable;
/*  31:    */ import org.primefaces.context.RequestContext;
/*  32:    */ import org.primefaces.model.LazyDataModel;
/*  33:    */ import org.primefaces.model.SortOrder;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class InterfazContableDespachoBean
/*  38:    */   extends PageControllerAS2
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = -2477748133217344720L;
/*  41:    */   @EJB
/*  42:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  43:    */   private LazyDataModel<InterfazContableProceso> listaInterfazContableProceso;
/*  44:    */   private DataTable dtDetalleAsiento;
/*  45:    */   private BigDecimal debe;
/*  46:    */   private BigDecimal haber;
/*  47:    */   private InterfazContableProceso interfazContableProceso;
/*  48: 68 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  49:    */   private DataTable dtInterfazContableProceso;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 76 */     this.listaInterfazContableProceso = new LazyDataModel()
/*  55:    */     {
/*  56:    */       private static final long serialVersionUID = 763093382591716471L;
/*  57:    */       
/*  58:    */       public List<InterfazContableProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */       {
/*  60: 83 */         List<InterfazContableProceso> lista = new ArrayList();
/*  61:    */         
/*  62: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  63: 86 */         filters.put("documentoBase", String.valueOf(DocumentoBase.INTERFAZ_DESPACHOS));
/*  64:    */         
/*  65: 88 */         lista = InterfazContableDespachoBean.this.servicioInterfazContableProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  66: 89 */         InterfazContableDespachoBean.this.listaInterfazContableProceso.setRowCount(InterfazContableDespachoBean.this.servicioInterfazContableProceso.contarPorCriterio(filters));
/*  67: 90 */         return lista;
/*  68:    */       }
/*  69:    */     };
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String crear()
/*  73:    */   {
/*  74:102 */     limpiar();
/*  75:103 */     setEditado(true);
/*  76:    */     
/*  77:105 */     return "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String editar()
/*  81:    */   {
/*  82:115 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  83:116 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String guardar()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:128 */       if (this.interfazContableProceso.getAsiento().getListaDetalleAsiento().isEmpty())
/*  91:    */       {
/*  92:129 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/*  93:    */       }
/*  94:    */       else
/*  95:    */       {
/*  96:132 */         this.servicioInterfazContableProceso.guardar(this.interfazContableProceso);
/*  97:    */         
/*  98:134 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  99:135 */         cargarDatos();
/* 100:    */         
/* 101:137 */         setEditado(false);
/* 102:    */       }
/* 103:    */     }
/* 104:    */     catch (ExcepcionAS2Financiero e)
/* 105:    */     {
/* 106:142 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 107:    */     }
/* 108:    */     catch (ExcepcionAS2 e)
/* 109:    */     {
/* 110:145 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 111:    */     }
/* 112:148 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String eliminar()
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:161 */       this.servicioInterfazContableProceso.anular(this.interfazContableProceso);
/* 120:    */       
/* 121:163 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 122:    */       
/* 123:165 */       cargarDatos();
/* 124:    */     }
/* 125:    */     catch (ExcepcionAS2Ventas e)
/* 126:    */     {
/* 127:168 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 128:    */     }
/* 129:    */     catch (ExcepcionAS2Financiero e)
/* 130:    */     {
/* 131:171 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 132:    */     }
/* 133:    */     catch (ExcepcionAS2 e)
/* 134:    */     {
/* 135:173 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 136:174 */       LOG.info(e);
/* 137:    */     }
/* 138:177 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String limpiar()
/* 142:    */   {
/* 143:187 */     setEditado(false);
/* 144:188 */     this.interfazContableProceso = new InterfazContableProceso();
/* 145:189 */     this.interfazContableProceso.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 146:190 */     this.interfazContableProceso.setSucursal(AppUtil.getSucursal());
/* 147:191 */     this.interfazContableProceso.setFechaDesde(Calendar.getInstance().getTime());
/* 148:192 */     this.interfazContableProceso.setFechaHasta(Calendar.getInstance().getTime());
/* 149:193 */     this.interfazContableProceso.setEstado(Estado.ELABORADO);
/* 150:194 */     this.interfazContableProceso.setDocumentoBase(DocumentoBase.INTERFAZ_DESPACHOS);
/* 151:195 */     this.interfazContableProceso.setAsiento(new Asiento());
/* 152:196 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String preliminarInterfazProceso()
/* 156:    */   {
/* 157:    */     try
/* 158:    */     {
/* 159:201 */       this.servicioInterfazContableProceso.generarAsiento(this.interfazContableProceso);
/* 160:202 */       calcular();
/* 161:    */     }
/* 162:    */     catch (ExcepcionAS2Financiero e)
/* 163:    */     {
/* 164:204 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 165:    */     }
/* 166:    */     catch (AS2Exception e)
/* 167:    */     {
/* 168:206 */       this.exContabilizacion = e;
/* 169:207 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 170:208 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 171:    */     }
/* 172:    */     catch (ExcepcionAS2 e)
/* 173:    */     {
/* 174:210 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 175:211 */       LOG.info("ERROR AL GENERAR-INTERFAZ DESPACHOS:", e);
/* 176:    */     }
/* 177:213 */     return "";
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String cargarDatos()
/* 181:    */   {
/* 182:218 */     setEditado(false);
/* 183:219 */     limpiar();
/* 184:220 */     return null;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 188:    */   {
/* 189:224 */     List<DetalleAsiento> lista = new ArrayList();
/* 190:226 */     if (this.interfazContableProceso.getAsiento() != null) {
/* 191:227 */       for (DetalleAsiento da : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 192:229 */         if (!da.isEliminado()) {
/* 193:230 */           lista.add(da);
/* 194:    */         }
/* 195:    */       }
/* 196:    */     }
/* 197:234 */     return lista;
/* 198:    */   }
/* 199:    */   
/* 200:    */   private void calcular()
/* 201:    */   {
/* 202:238 */     this.debe = BigDecimal.ZERO;
/* 203:239 */     this.haber = BigDecimal.ZERO;
/* 204:241 */     if (this.interfazContableProceso.getAsiento() != null) {
/* 205:242 */       for (DetalleAsiento d : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 206:243 */         if (!d.isEliminado())
/* 207:    */         {
/* 208:244 */           this.haber = this.haber.add(d.getHaber());
/* 209:245 */           this.debe = this.debe.add(d.getDebe());
/* 210:    */         }
/* 211:    */       }
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public DataTable getDtDetalleAsiento()
/* 216:    */   {
/* 217:252 */     return this.dtDetalleAsiento;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setDtDetalleAsiento(DataTable dtDetalleAsiento)
/* 221:    */   {
/* 222:256 */     this.dtDetalleAsiento = dtDetalleAsiento;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public BigDecimal getDebe()
/* 226:    */   {
/* 227:261 */     calcular();
/* 228:    */     
/* 229:263 */     return this.debe;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setDebe(BigDecimal debe)
/* 233:    */   {
/* 234:267 */     this.debe = debe;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public BigDecimal getHaber()
/* 238:    */   {
/* 239:272 */     calcular();
/* 240:    */     
/* 241:274 */     return this.haber;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setHaber(BigDecimal haber)
/* 245:    */   {
/* 246:278 */     this.haber = haber;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public InterfazContableProceso getInterfazContableProceso()
/* 250:    */   {
/* 251:282 */     return this.interfazContableProceso;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 255:    */   {
/* 256:286 */     this.interfazContableProceso = interfazContableProceso;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public LazyDataModel<InterfazContableProceso> getListaInterfazContableProceso()
/* 260:    */   {
/* 261:290 */     return this.listaInterfazContableProceso;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setListaInterfazContableProceso(LazyDataModel<InterfazContableProceso> listaInterfazContableProceso)
/* 265:    */   {
/* 266:294 */     this.listaInterfazContableProceso = listaInterfazContableProceso;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public DataTable getDtInterfazContableProceso()
/* 270:    */   {
/* 271:298 */     return this.dtInterfazContableProceso;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setDtInterfazContableProceso(DataTable dtInterfazContableProceso)
/* 275:    */   {
/* 276:302 */     this.dtInterfazContableProceso = dtInterfazContableProceso;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public AS2Exception getExContabilizacion()
/* 280:    */   {
/* 281:306 */     return this.exContabilizacion;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 285:    */   {
/* 286:310 */     this.exContabilizacion = exContabilizacion;
/* 287:    */   }
/* 288:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.InterfazContableDespachoBean
 * JD-Core Version:    0.7.0.1
 */