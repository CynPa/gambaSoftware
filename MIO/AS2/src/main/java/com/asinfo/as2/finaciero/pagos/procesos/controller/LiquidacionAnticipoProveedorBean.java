/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*   8:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoProveedor;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.LiquidacionAnticipoProveedor;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioLiquidacionAnticipoProveedor;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import java.math.BigDecimal;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.List;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ import org.primefaces.component.datatable.DataTable;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class LiquidacionAnticipoProveedorBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 965268038798712432L;
/*  35:    */   @EJB
/*  36:    */   private ServicioLiquidacionAnticipoProveedor servicioLiquidacionAnticipoProveedor;
/*  37:    */   @EJB
/*  38:    */   private ServicioDocumento servicioDocumento;
/*  39:    */   @EJB
/*  40:    */   private ServicioEmpresa servicioEmpresa;
/*  41:    */   private LiquidacionAnticipoProveedor liquidacionAnticipoProveedor;
/*  42:    */   private AnticipoProveedor anticipoProveedor;
/*  43:    */   private List<LiquidacionAnticipoProveedor> listaLiquidacionAnticipoProveedor;
/*  44: 65 */   private BigDecimal totalLiquidado = BigDecimal.ZERO;
/*  45:    */   private List<Empresa> listaEmpresa;
/*  46:    */   private List<Documento> listaDocumento;
/*  47:    */   private List<AnticipoProveedor> listaAnticipoProveedor;
/*  48:    */   private DataTable dtLiquidacionAnticipoProveedor;
/*  49:    */   private DataTable dtDetalleLiquidacionAnticipoProveedor;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 78 */     this.anticipoProveedor = ((AnticipoProveedor)AppUtil.getAtributo("anticipo_proveedor"));
/*  55: 79 */     getLiquidacionAnticipoProveedor().setAnticipoProveedor(this.anticipoProveedor);
/*  56: 80 */     setEditado(true);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String cancelar()
/*  60:    */   {
/*  61: 85 */     return "anticipoProveedor?faces-redirect=true";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String editar()
/*  65:    */   {
/*  66: 95 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  67: 96 */     return "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String guardar()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:108 */       this.servicioLiquidacionAnticipoProveedor.guardar(this.liquidacionAnticipoProveedor);
/*  75:    */       
/*  76:110 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  77:    */       
/*  78:112 */       return "anticipoProveedor?faces-redirect=true";
/*  79:    */     }
/*  80:    */     catch (ExcepcionAS2Financiero e)
/*  81:    */     {
/*  82:114 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  83:115 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  84:116 */       return "";
/*  85:    */     }
/*  86:    */     catch (ExcepcionAS2 e)
/*  87:    */     {
/*  88:118 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  89:119 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  90:120 */       return "";
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  95:123 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  96:    */     }
/*  97:125 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String eliminar()
/* 101:    */   {
/* 102:137 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String limpiar()
/* 106:    */   {
/* 107:147 */     crearLiquidacionAnticipoProveedor();
/* 108:148 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String cargarDatos()
/* 112:    */   {
/* 113:158 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   private void crearLiquidacionAnticipoProveedor()
/* 117:    */   {
/* 118:162 */     this.liquidacionAnticipoProveedor = new LiquidacionAnticipoProveedor();
/* 119:163 */     this.liquidacionAnticipoProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 120:164 */     this.liquidacionAnticipoProveedor.setFecha(new Date());
/* 121:165 */     this.liquidacionAnticipoProveedor.setEstado(Estado.ELABORADO);
/* 122:166 */     this.liquidacionAnticipoProveedor.setAnticipoProveedor(new AnticipoProveedor());
/* 123:167 */     this.liquidacionAnticipoProveedor.setNumero("");
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String actualizarDocumento()
/* 127:    */   {
/* 128:177 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getLiquidacionAnticipoProveedor().getDocumento().getIdDocumento()));
/* 129:178 */     getLiquidacionAnticipoProveedor().setDocumento(documento);
/* 130:    */     
/* 131:180 */     return "";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void cargarFacturasPendientes()
/* 135:    */   {
/* 136:189 */     this.servicioLiquidacionAnticipoProveedor.cargarFacturasPendientes(this.liquidacionAnticipoProveedor, 0);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public LiquidacionAnticipoProveedor getLiquidacionAnticipoProveedor()
/* 140:    */   {
/* 141:193 */     if (this.liquidacionAnticipoProveedor == null) {
/* 142:194 */       crearLiquidacionAnticipoProveedor();
/* 143:    */     }
/* 144:196 */     return this.liquidacionAnticipoProveedor;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setLiquidacionAnticipoProveedor(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor)
/* 148:    */   {
/* 149:200 */     this.liquidacionAnticipoProveedor = liquidacionAnticipoProveedor;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<LiquidacionAnticipoProveedor> getListaLiquidacionAnticipoProveedor()
/* 153:    */   {
/* 154:204 */     if (this.listaLiquidacionAnticipoProveedor == null) {
/* 155:205 */       cargarDatos();
/* 156:    */     }
/* 157:207 */     return this.listaLiquidacionAnticipoProveedor;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaLiquidacionAnticipoProveedor(List<LiquidacionAnticipoProveedor> listaLiquidacionAnticipoProveedor)
/* 161:    */   {
/* 162:211 */     this.listaLiquidacionAnticipoProveedor = listaLiquidacionAnticipoProveedor;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<Empresa> getListaEmpresa()
/* 166:    */   {
/* 167:215 */     if (this.listaEmpresa == null) {
/* 168:216 */       this.listaEmpresa = this.servicioEmpresa.obtenerProveedores();
/* 169:    */     }
/* 170:219 */     return this.listaEmpresa;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setListaEmpresa(List<Empresa> listaEmpresa)
/* 174:    */   {
/* 175:223 */     this.listaEmpresa = listaEmpresa;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<Documento> getListaDocumento()
/* 179:    */   {
/* 180:227 */     if (this.listaDocumento == null) {
/* 181:    */       try
/* 182:    */       {
/* 183:229 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.LIQUIDACION_ANTICIPO_PROVEEDOR);
/* 184:    */       }
/* 185:    */       catch (ExcepcionAS2 e)
/* 186:    */       {
/* 187:231 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 188:    */       }
/* 189:    */     }
/* 190:234 */     return this.listaDocumento;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 194:    */   {
/* 195:238 */     this.listaDocumento = listaDocumento;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<AnticipoProveedor> getListaAnticipoProveedor()
/* 199:    */   {
/* 200:242 */     return this.listaAnticipoProveedor;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setListaAnticipoProveedor(List<AnticipoProveedor> listaAnticipoProveedor)
/* 204:    */   {
/* 205:246 */     this.listaAnticipoProveedor = listaAnticipoProveedor;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public DataTable getDtLiquidacionAnticipoProveedor()
/* 209:    */   {
/* 210:250 */     return this.dtLiquidacionAnticipoProveedor;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setDtLiquidacionAnticipoProveedor(DataTable dtLiquidacionAnticipoProveedor)
/* 214:    */   {
/* 215:254 */     this.dtLiquidacionAnticipoProveedor = dtLiquidacionAnticipoProveedor;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public DataTable getDtDetalleLiquidacionAnticipoProveedor()
/* 219:    */   {
/* 220:258 */     return this.dtDetalleLiquidacionAnticipoProveedor;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setDtDetalleLiquidacionAnticipoProveedor(DataTable dtDetalleLiquidacionAnticipoProveedor)
/* 224:    */   {
/* 225:262 */     this.dtDetalleLiquidacionAnticipoProveedor = dtDetalleLiquidacionAnticipoProveedor;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<DetalleLiquidacionAnticipoProveedor> getListaDetalleLiquidacionAnticipoProveedor()
/* 229:    */   {
/* 230:267 */     return getLiquidacionAnticipoProveedor().getListaDetalleLiquidacionAnticipoProveedor();
/* 231:    */   }
/* 232:    */   
/* 233:    */   public BigDecimal getTotalLiquidado()
/* 234:    */   {
/* 235:271 */     this.totalLiquidado = BigDecimal.ZERO;
/* 236:273 */     for (DetalleLiquidacionAnticipoProveedor detallePago : getLiquidacionAnticipoProveedor().getListaDetalleLiquidacionAnticipoProveedor()) {
/* 237:274 */       if (!detallePago.isEliminado()) {
/* 238:275 */         this.totalLiquidado = this.totalLiquidado.add(detallePago.getValor());
/* 239:    */       }
/* 240:    */     }
/* 241:278 */     return this.totalLiquidado;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setTotalLiquidado(BigDecimal totalLiquidado)
/* 245:    */   {
/* 246:282 */     this.totalLiquidado = totalLiquidado;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public AnticipoProveedor getAnticipoProveedor()
/* 250:    */   {
/* 251:286 */     return this.anticipoProveedor;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/* 255:    */   {
/* 256:290 */     this.anticipoProveedor = anticipoProveedor;
/* 257:    */   }
/* 258:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.LiquidacionAnticipoProveedorBean
 * JD-Core Version:    0.7.0.1
 */