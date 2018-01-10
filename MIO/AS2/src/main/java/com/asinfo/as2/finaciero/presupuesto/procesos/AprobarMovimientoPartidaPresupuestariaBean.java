/*   1:    */ package com.asinfo.as2.finaciero.presupuesto.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Documento;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.presupuesto.MovimientoPartidaPresupuestaria;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  12:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioMovimientoPartidaPresupuestaria;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import com.asinfo.as2.utils.JsfUtil;
/*  18:    */ import java.text.SimpleDateFormat;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import javax.faces.model.SelectItem;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ import org.primefaces.component.datatable.DataTable;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class AprobarMovimientoPartidaPresupuestariaBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = -1580967030201020171L;
/*  35:    */   @EJB
/*  36:    */   private ServicioMovimientoPartidaPresupuestaria servicioMovimientoPartidaPresupuestaria;
/*  37:    */   @EJB
/*  38:    */   private ServicioDocumento servicioDocumento;
/*  39:    */   private MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria;
/*  40:    */   private Documento documento;
/*  41:    */   private List<MovimientoPartidaPresupuestaria> listaMovimientoPartidaPresupuestaria;
/*  42:    */   private List<Documento> listaDocumento;
/*  43:    */   private SelectItem[] listaEstadoItem;
/*  44:    */   private Estado estado;
/*  45: 51 */   private boolean renderColumns = true;
/*  46: 52 */   private Date fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/*  47: 53 */   private Date fechaHasta = new Date();
/*  48:    */   private DataTable dtMovimiento;
/*  49:    */   
/*  50:    */   public String editar()
/*  51:    */   {
/*  52: 59 */     return null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String guardar()
/*  56:    */   {
/*  57: 64 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String eliminar()
/*  61:    */   {
/*  62: 69 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String limpiar()
/*  66:    */   {
/*  67: 74 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String cargarDatos()
/*  71:    */   {
/*  72: 79 */     return null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String procesarMovimientos()
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79: 84 */       this.servicioMovimientoPartidaPresupuestaria.procesarMovimientos(this.listaMovimientoPartidaPresupuestaria, this.estado);
/*  80: 85 */       cargarMovimientos();
/*  81:    */     }
/*  82:    */     catch (AS2Exception e)
/*  83:    */     {
/*  84: 87 */       JsfUtil.addErrorMessage(e, "");
/*  85: 88 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/*  86: 89 */       e.printStackTrace();
/*  87:    */     }
/*  88:    */     catch (ExcepcionAS2 e)
/*  89:    */     {
/*  90: 91 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  91: 92 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/*  92: 93 */       e.printStackTrace();
/*  93:    */     }
/*  94: 96 */     return null;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void cargaDatosMovimiento()
/*  98:    */   {
/*  99:100 */     this.movimientoPartidaPresupuestaria = ((MovimientoPartidaPresupuestaria)getDtMovimiento().getRowData());
/* 100:101 */     if ((this.movimientoPartidaPresupuestaria != null) && (this.movimientoPartidaPresupuestaria.getId() > 0)) {
/* 101:    */       try
/* 102:    */       {
/* 103:103 */         this.movimientoPartidaPresupuestaria = this.servicioMovimientoPartidaPresupuestaria.cargarDetalle(this.movimientoPartidaPresupuestaria.getId());
/* 104:104 */         actualizarPanelDetalle();
/* 105:    */       }
/* 106:    */       catch (Exception e)
/* 107:    */       {
/* 108:106 */         addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 109:107 */         LOG.error("ERROR AL CARGAR MOVIMIENTO PARTIDA PRESUPUESTARIA:", e);
/* 110:    */       }
/* 111:    */     } else {
/* 112:110 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String cargarMovimientos()
/* 117:    */   {
/* 118:116 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 119:117 */     if (this.documento != null) {
/* 120:118 */       filters.put("documento.idDocumento", "" + this.documento.getId());
/* 121:    */     }
/* 122:119 */     filters.put("estado", Estado.ELABORADO.name());
/* 123:120 */     SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
/* 124:121 */     filters.put("fecha", OperacionEnum.BETWEEN.name() + sdf2.format(this.fechaDesde) + "~" + sdf2.format(this.fechaHasta));
/* 125:122 */     this.listaMovimientoPartidaPresupuestaria = this.servicioMovimientoPartidaPresupuestaria.obtenerListaPorPagina(0, 1000, "fecha", true, filters);
/* 126:123 */     return null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String actualizarPanelDetalle()
/* 130:    */   {
/* 131:127 */     if ((getMovimientoPartidaPresupuestaria().getDocumento() != null) && (getMovimientoPartidaPresupuestaria().getDocumento().getOperacion() == 0)) {
/* 132:128 */       this.renderColumns = true;
/* 133:    */     } else {
/* 134:130 */       this.renderColumns = false;
/* 135:    */     }
/* 136:132 */     return null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public MovimientoPartidaPresupuestaria getMovimientoPartidaPresupuestaria()
/* 140:    */   {
/* 141:136 */     return this.movimientoPartidaPresupuestaria;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setMovimientoPartidaPresupuestaria(MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria)
/* 145:    */   {
/* 146:140 */     this.movimientoPartidaPresupuestaria = movimientoPartidaPresupuestaria;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean isRenderColumns()
/* 150:    */   {
/* 151:144 */     return this.renderColumns;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setRenderColumns(boolean renderColumns)
/* 155:    */   {
/* 156:148 */     this.renderColumns = renderColumns;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Date getFechaDesde()
/* 160:    */   {
/* 161:152 */     return this.fechaDesde;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setFechaDesde(Date fechaDesde)
/* 165:    */   {
/* 166:156 */     this.fechaDesde = fechaDesde;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Date getFechaHasta()
/* 170:    */   {
/* 171:160 */     return this.fechaHasta;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setFechaHasta(Date fechaHasta)
/* 175:    */   {
/* 176:164 */     this.fechaHasta = fechaHasta;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public List<Documento> getListaDocumento()
/* 180:    */   {
/* 181:    */     try
/* 182:    */     {
/* 183:169 */       if (this.listaDocumento == null)
/* 184:    */       {
/* 185:170 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.TRANSFERENCIA_PARTIDA_PRESUPUESTARIA, 
/* 186:171 */           AppUtil.getOrganizacion().getId());
/* 187:172 */         this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.MOVIMIENTO_PARTIDA_PRESUPUESTARIA, 
/* 188:173 */           AppUtil.getOrganizacion().getId()));
/* 189:    */       }
/* 190:    */     }
/* 191:    */     catch (ExcepcionAS2 e)
/* 192:    */     {
/* 193:176 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 194:    */     }
/* 195:179 */     return this.listaDocumento;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Documento getDocumento()
/* 199:    */   {
/* 200:183 */     return this.documento;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setDocumento(Documento documento)
/* 204:    */   {
/* 205:187 */     this.documento = documento;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public List<MovimientoPartidaPresupuestaria> getListaMovimientoPartidaPresupuestaria()
/* 209:    */   {
/* 210:191 */     return this.listaMovimientoPartidaPresupuestaria;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setListaMovimientoPartidaPresupuestaria(List<MovimientoPartidaPresupuestaria> listaMovimientoPartidaPresupuestaria)
/* 214:    */   {
/* 215:195 */     this.listaMovimientoPartidaPresupuestaria = listaMovimientoPartidaPresupuestaria;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public SelectItem[] getListaEstadoItem()
/* 219:    */   {
/* 220:200 */     SelectItem[] items = new SelectItem[2];
/* 221:201 */     items[0] = new SelectItem(Estado.APROBADO, Estado.APROBADO.getNombre());
/* 222:202 */     items[1] = new SelectItem(Estado.ANULADO, Estado.ANULADO.getNombre());
/* 223:203 */     this.listaEstadoItem = items;
/* 224:204 */     return this.listaEstadoItem;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public Estado getEstado()
/* 228:    */   {
/* 229:208 */     return this.estado;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setEstado(Estado estado)
/* 233:    */   {
/* 234:212 */     this.estado = estado;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public DataTable getDtMovimiento()
/* 238:    */   {
/* 239:216 */     return this.dtMovimiento;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setDtMovimiento(DataTable dtMovimiento)
/* 243:    */   {
/* 244:220 */     this.dtMovimiento = dtMovimiento;
/* 245:    */   }
/* 246:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.presupuesto.procesos.AprobarMovimientoPartidaPresupuestariaBean
 * JD-Core Version:    0.7.0.1
 */