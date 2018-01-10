/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*   7:    */ import com.asinfo.as2.entities.DetalleOrdenPagoProveedor;
/*   8:    */ import com.asinfo.as2.entities.OrdenPagoProveedor;
/*   9:    */ import com.asinfo.as2.entities.Proveedor;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioOrdenPagoProveedor;
/*  13:    */ import com.asinfo.as2.utils.JsfUtil;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import javax.faces.event.ActionEvent;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class AprobarOrdenPagoProveedorBean
/*  27:    */   extends OrdenPagoProveedorBean
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 7085091448710210515L;
/*  30:    */   private List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoAprobar;
/*  31:    */   
/*  32:    */   @PostConstruct
/*  33:    */   public void init()
/*  34:    */   {
/*  35: 49 */     this.indicadorAprobar = true;
/*  36: 50 */     super.init();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String crear()
/*  40:    */   {
/*  41: 55 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  42: 56 */     return "";
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String eliminar()
/*  46:    */   {
/*  47: 61 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  48: 62 */     return "";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String editar()
/*  52:    */   {
/*  53: 67 */     if ((this.ordenPagoProveedor != null) && (this.ordenPagoProveedor.getId() != 0))
/*  54:    */     {
/*  55: 68 */       this.ordenPagoProveedor = this.servicioOrdenPagoProveedor.cargarDetalle(this.ordenPagoProveedor.getId());
/*  56: 69 */       this.listaDetalleOrdenPagoProveedorFilters = getListaDetalleOrdenPagoProveedor();
/*  57:    */       try
/*  58:    */       {
/*  59: 71 */         this.servicioOrdenPagoProveedor.isEditable(this.ordenPagoProveedor);
/*  60:    */       }
/*  61:    */       catch (AS2Exception e)
/*  62:    */       {
/*  63: 73 */         JsfUtil.addErrorMessage(e, "");
/*  64: 74 */         return "";
/*  65:    */       }
/*  66: 76 */       if ((!this.ordenPagoProveedor.getEstado().equals(Estado.APROBADO)) && (!Estado.ELABORADO.equals(this.ordenPagoProveedor.getEstado())))
/*  67:    */       {
/*  68: 77 */         setEditado(true);
/*  69: 78 */         this.dtDetalleOrdenPagoProveedor.reset();
/*  70: 79 */         agruparMapas();
/*  71: 80 */         calculoTotales();
/*  72:    */       }
/*  73:    */       else
/*  74:    */       {
/*  75: 82 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " - " + this.ordenPagoProveedor.getEstado());
/*  76:    */       }
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80: 85 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  81:    */     }
/*  82: 87 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void cargarFacturasPendientesNoVencidas(ActionEvent event)
/*  86:    */   {
/*  87: 91 */     List<CuentaPorPagar> lista = this.servicioFacturaProveedor.obtenerFacturasPendientes(0, 0, null, null, null, 0, null, null, 
/*  88: 92 */       Integer.valueOf(this.ordenPagoProveedor.getIdOrganizacion()), null, Boolean.valueOf(false));
/*  89: 93 */     this.listaFacturasPendientesNoVencidas = new ArrayList();
/*  90: 95 */     for (CuentaPorPagar cuentaPorPagar : lista)
/*  91:    */     {
/*  92: 96 */       boolean encontre = false;
/*  93: 97 */       for (DetalleOrdenPagoProveedor detalle : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/*  94: 98 */         if ((!detalle.isEliminado()) && (detalle.getCuentaPorPagar() != null) && (detalle.getCuentaPorPagar().getId() == cuentaPorPagar.getId()))
/*  95:    */         {
/*  96: 99 */           encontre = true;
/*  97:100 */           break;
/*  98:    */         }
/*  99:    */       }
/* 100:103 */       if (!encontre) {
/* 101:104 */         this.listaFacturasPendientesNoVencidas.add(cuentaPorPagar);
/* 102:    */       }
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public DetalleOrdenPagoProveedor agregarAnticipo()
/* 107:    */   {
/* 108:115 */     DetalleOrdenPagoProveedor detalleOrdenpago = super.agregarAnticipo();
/* 109:116 */     if (detalleOrdenpago != null)
/* 110:    */     {
/* 111:117 */       detalleOrdenpago.setValorAprobado(detalleOrdenpago.getValor());
/* 112:118 */       detalleOrdenpago.setValor(BigDecimal.ZERO);
/* 113:119 */       detalleOrdenpago.setIndicadorAprobacionManual(true);
/* 114:120 */       calculoTotales();
/* 115:    */     }
/* 116:122 */     setIndicadorRenderedAnticipo(false);
/* 117:123 */     return detalleOrdenpago;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public List<DetalleOrdenPagoProveedor> getListaDetalleOrdenPagoProveedor()
/* 121:    */   {
/* 122:127 */     this.listaDetalleOrdenPagoProveedor = new ArrayList();
/* 123:128 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 124:129 */       if (!detalleOrdenPago.isEliminado()) {
/* 125:130 */         this.listaDetalleOrdenPagoProveedor.add(detalleOrdenPago);
/* 126:    */       }
/* 127:    */     }
/* 128:133 */     return this.listaDetalleOrdenPagoProveedor;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void cargarValorCuota()
/* 132:    */   {
/* 133:137 */     DetalleOrdenPagoProveedor detalleOrdenPago = (DetalleOrdenPagoProveedor)this.dtDetalleOrdenPagoProveedor.getRowData();
/* 134:138 */     if (!detalleOrdenPago.isIndicadorAprobacionManual())
/* 135:    */     {
/* 136:139 */       detalleOrdenPago.setValorAprobado(detalleOrdenPago.getValor());
/* 137:140 */       calculoTotales();
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void cargarValorCuotaGlobal()
/* 142:    */   {
/* 143:145 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 144:146 */       if (!detalleOrdenPago.isIndicadorAprobacionManual()) {
/* 145:147 */         detalleOrdenPago.setValorAprobado(detalleOrdenPago.getValor());
/* 146:    */       }
/* 147:    */     }
/* 148:150 */     calculoTotales();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void limpiarValorCuota()
/* 152:    */   {
/* 153:154 */     DetalleOrdenPagoProveedor detalleOrdenPago = (DetalleOrdenPagoProveedor)this.dtDetalleOrdenPagoProveedor.getRowData();
/* 154:155 */     detalleOrdenPago.setValorAprobado(BigDecimal.ZERO);
/* 155:156 */     calculoTotales();
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void limpiarValorCuotaGlobal()
/* 159:    */   {
/* 160:160 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 161:161 */       detalleOrdenPago.setValorAprobado(BigDecimal.ZERO);
/* 162:    */     }
/* 163:163 */     calculoTotales();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void cargarValorCuotaGlobalProveedor(CategoriaEmpresa categoriaEmpresa)
/* 167:    */   {
/* 168:167 */     for (Proveedor proveedor : categoriaEmpresa.getListaProveedor()) {
/* 169:168 */       for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 170:169 */         if (!detalleOrdenPago.isIndicadorAprobacionManual()) {
/* 171:170 */           detalleOrdenPago.setValorAprobado(detalleOrdenPago.getValor());
/* 172:    */         }
/* 173:    */       }
/* 174:    */     }
/* 175:174 */     calculoTotales();
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void cargarValorCuotaProveedor()
/* 179:    */   {
/* 180:178 */     Proveedor proveedor = (Proveedor)this.dtDetalleProveedor.getRowData();
/* 181:179 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 182:180 */       if (!detalleOrdenPago.isIndicadorAprobacionManual()) {
/* 183:181 */         detalleOrdenPago.setValorAprobado(detalleOrdenPago.getValor());
/* 184:    */       }
/* 185:    */     }
/* 186:184 */     calculoTotales();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void limpiarValorCuotaGlobalProveedor(CategoriaEmpresa categoriaEmpresa)
/* 190:    */   {
/* 191:188 */     for (Proveedor proveedor : categoriaEmpresa.getListaProveedor()) {
/* 192:189 */       for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 193:190 */         detalleOrdenPago.setValorAprobado(BigDecimal.ZERO);
/* 194:    */       }
/* 195:    */     }
/* 196:193 */     calculoTotales();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void limpiarValorCuotaProveedor()
/* 200:    */   {
/* 201:197 */     Proveedor proveedor = (Proveedor)this.dtDetalleProveedor.getRowData();
/* 202:198 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 203:199 */       detalleOrdenPago.setValorAprobado(BigDecimal.ZERO);
/* 204:    */     }
/* 205:201 */     calculoTotales();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void aprobarOrdenPagoProveedor()
/* 209:    */   {
/* 210:    */     try
/* 211:    */     {
/* 212:206 */       this.servicioOrdenPagoProveedor.aprobarOrdenPagoProveedor(getOrdenPagoProveedor());
/* 213:207 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 214:208 */       limpiar();
/* 215:    */     }
/* 216:    */     catch (Exception e)
/* 217:    */     {
/* 218:210 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 219:211 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void seleccionarOrdenPagoProveedor()
/* 224:    */   {
/* 225:216 */     setOrdenPagoProveedor(this.servicioOrdenPagoProveedor.cargarDetalle(getOrdenPagoProveedor().getId()));
/* 226:217 */     this.listaDetalleOrdenPagoAprobar = new ArrayList();
/* 227:218 */     for (DetalleOrdenPagoProveedor detalleOrdenPagoProveedor : getOrdenPagoProveedor().getListaDetalleOrdenPagoProveedor()) {
/* 228:219 */       if (detalleOrdenPagoProveedor.getValorAprobado().compareTo(BigDecimal.ZERO) != 0) {
/* 229:220 */         this.listaDetalleOrdenPagoAprobar.add(detalleOrdenPagoProveedor);
/* 230:    */       }
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public List<DetalleOrdenPagoProveedor> getListaDetalleOrdenPagoAprobar()
/* 235:    */   {
/* 236:227 */     return this.listaDetalleOrdenPagoAprobar;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setListaDetalleOrdenPagoAprobar(List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoAprobar)
/* 240:    */   {
/* 241:231 */     this.listaDetalleOrdenPagoAprobar = listaDetalleOrdenPagoAprobar;
/* 242:    */   }
/* 243:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.AprobarOrdenPagoProveedorBean
 * JD-Core Version:    0.7.0.1
 */