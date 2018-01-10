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
/*  16:    */ import com.asinfo.as2.utils.JsfUtil;
/*  17:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Calendar;
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
/*  34:    */ public class InterfazContableCostoVentasBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -2477748133217344720L;
/*  38:    */   @EJB
/*  39:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  40:    */   private BigDecimal debe;
/*  41:    */   private BigDecimal haber;
/*  42:    */   private InterfazContableProceso interfazContableProceso;
/*  43:    */   private LazyDataModel<InterfazContableProceso> listaInterfazContableProceso;
/*  44:    */   private DataTable dtInterfazContableProceso;
/*  45:    */   private DataTable dtDetalleAsiento;
/*  46:    */   
/*  47:    */   @PostConstruct
/*  48:    */   public void init()
/*  49:    */   {
/*  50: 82 */     this.listaInterfazContableProceso = new LazyDataModel()
/*  51:    */     {
/*  52:    */       private static final long serialVersionUID = 763093382591716471L;
/*  53:    */       
/*  54:    */       public List<InterfazContableProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  55:    */       {
/*  56: 89 */         List<InterfazContableProceso> lista = new ArrayList();
/*  57:    */         
/*  58: 91 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  59: 92 */         filters.put("documentoBase", String.valueOf(DocumentoBase.INTERFAZ_DEVOLUCION_COSTO_VENTAS));
/*  60:    */         
/*  61: 94 */         lista = InterfazContableCostoVentasBean.this.servicioInterfazContableProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  62: 95 */         InterfazContableCostoVentasBean.this.listaInterfazContableProceso.setRowCount(InterfazContableCostoVentasBean.this.servicioInterfazContableProceso.contarPorCriterio(filters));
/*  63: 96 */         return lista;
/*  64:    */       }
/*  65:    */     };
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String crear()
/*  69:    */   {
/*  70:108 */     limpiar();
/*  71:109 */     setEditado(true);
/*  72:    */     
/*  73:111 */     return "";
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String editar()
/*  77:    */   {
/*  78:121 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  79:122 */     return "";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String guardar()
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:134 */       if (this.interfazContableProceso.getAsiento().getListaDetalleAsiento().isEmpty())
/*  87:    */       {
/*  88:135 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/*  89:    */       }
/*  90:    */       else
/*  91:    */       {
/*  92:139 */         this.servicioInterfazContableProceso.guardar(this.interfazContableProceso);
/*  93:140 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  94:141 */         cargarDatos();
/*  95:    */       }
/*  96:    */     }
/*  97:    */     catch (ExcepcionAS2Financiero e)
/*  98:    */     {
/*  99:145 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 100:    */     }
/* 101:    */     catch (ExcepcionAS2 e)
/* 102:    */     {
/* 103:148 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 104:    */     }
/* 105:151 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String eliminar()
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:164 */       this.servicioInterfazContableProceso.anular(this.interfazContableProceso);
/* 113:165 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 114:166 */       cargarDatos();
/* 115:    */     }
/* 116:    */     catch (ExcepcionAS2Ventas e)
/* 117:    */     {
/* 118:169 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()), e.getMessage());
/* 119:    */     }
/* 120:    */     catch (ExcepcionAS2Financiero e)
/* 121:    */     {
/* 122:172 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 123:    */     }
/* 124:    */     catch (ExcepcionAS2 e)
/* 125:    */     {
/* 126:174 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()), e.getMessage());
/* 127:175 */       LOG.info(e);
/* 128:    */     }
/* 129:178 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String limpiar()
/* 133:    */   {
/* 134:188 */     setEditado(false);
/* 135:189 */     this.interfazContableProceso = new InterfazContableProceso();
/* 136:190 */     this.interfazContableProceso.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 137:191 */     this.interfazContableProceso.setSucursal(AppUtil.getSucursal());
/* 138:192 */     this.interfazContableProceso.setFechaDesde(Calendar.getInstance().getTime());
/* 139:193 */     this.interfazContableProceso.setFechaHasta(Calendar.getInstance().getTime());
/* 140:194 */     this.interfazContableProceso.setEstado(Estado.ELABORADO);
/* 141:195 */     this.interfazContableProceso.setDocumentoBase(DocumentoBase.INTERFAZ_DEVOLUCION_COSTO_VENTAS);
/* 142:196 */     this.interfazContableProceso.setFiltroDocumentoBase(DocumentoBase.DEVOLUCION_CLIENTE);
/* 143:197 */     this.interfazContableProceso.setAsiento(new Asiento());
/* 144:198 */     return "";
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String preliminarInterfazProceso()
/* 148:    */   {
/* 149:    */     try
/* 150:    */     {
/* 151:203 */       this.servicioInterfazContableProceso.generarAsiento(this.interfazContableProceso);
/* 152:    */     }
/* 153:    */     catch (ExcepcionAS2Financiero e)
/* 154:    */     {
/* 155:205 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 156:    */     }
/* 157:    */     catch (ExcepcionAS2 e)
/* 158:    */     {
/* 159:207 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 160:208 */       LOG.info("ERROR AL GENERAR-INTERFAZ DESPACHOS:", e);
/* 161:    */     }
/* 162:    */     catch (AS2Exception e)
/* 163:    */     {
/* 164:210 */       JsfUtil.addErrorMessage(e, "");
/* 165:    */     }
/* 166:212 */     return "";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String cargarDatos()
/* 170:    */   {
/* 171:217 */     setEditado(false);
/* 172:218 */     limpiar();
/* 173:219 */     return null;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 177:    */   {
/* 178:223 */     List<DetalleAsiento> lista = new ArrayList();
/* 179:224 */     if (this.interfazContableProceso.getAsiento() != null) {
/* 180:225 */       for (DetalleAsiento da : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 181:226 */         if (!da.isEliminado()) {
/* 182:227 */           lista.add(da);
/* 183:    */         }
/* 184:    */       }
/* 185:    */     }
/* 186:231 */     return lista;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private void calcular()
/* 190:    */   {
/* 191:235 */     this.debe = BigDecimal.ZERO;
/* 192:236 */     this.haber = BigDecimal.ZERO;
/* 193:237 */     if (this.interfazContableProceso.getAsiento() != null) {
/* 194:238 */       for (DetalleAsiento d : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 195:239 */         if (!d.isEliminado())
/* 196:    */         {
/* 197:240 */           this.haber = this.haber.add(d.getHaber());
/* 198:241 */           this.debe = this.debe.add(d.getDebe());
/* 199:    */         }
/* 200:    */       }
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public DataTable getDtDetalleAsiento()
/* 205:    */   {
/* 206:248 */     return this.dtDetalleAsiento;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setDtDetalleAsiento(DataTable dtDetalleAsiento)
/* 210:    */   {
/* 211:252 */     this.dtDetalleAsiento = dtDetalleAsiento;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public BigDecimal getDebe()
/* 215:    */   {
/* 216:257 */     calcular();
/* 217:    */     
/* 218:259 */     return this.debe;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setDebe(BigDecimal debe)
/* 222:    */   {
/* 223:263 */     this.debe = debe;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public BigDecimal getHaber()
/* 227:    */   {
/* 228:268 */     calcular();
/* 229:    */     
/* 230:270 */     return this.haber;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setHaber(BigDecimal haber)
/* 234:    */   {
/* 235:274 */     this.haber = haber;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public InterfazContableProceso getInterfazContableProceso()
/* 239:    */   {
/* 240:278 */     return this.interfazContableProceso;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 244:    */   {
/* 245:282 */     this.interfazContableProceso = interfazContableProceso;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public LazyDataModel<InterfazContableProceso> getListaInterfazContableProceso()
/* 249:    */   {
/* 250:286 */     return this.listaInterfazContableProceso;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setListaInterfazContableProceso(LazyDataModel<InterfazContableProceso> listaInterfazContableProceso)
/* 254:    */   {
/* 255:290 */     this.listaInterfazContableProceso = listaInterfazContableProceso;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public DataTable getDtInterfazContableProceso()
/* 259:    */   {
/* 260:294 */     return this.dtInterfazContableProceso;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setDtInterfazContableProceso(DataTable dtInterfazContableProceso)
/* 264:    */   {
/* 265:298 */     this.dtInterfazContableProceso = dtInterfazContableProceso;
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.InterfazContableCostoVentasBean
 * JD-Core Version:    0.7.0.1
 */