/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.DetallePagoCuotaPrestamo;
/*   5:    */ import com.asinfo.as2.entities.DetallePrestamo;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.PagoRol;
/*   8:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*   9:    */ import com.asinfo.as2.entities.Prestamo;
/*  10:    */ import com.asinfo.as2.entities.TipoPrestamo;
/*  11:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  12:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  15:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPrestamo;
/*  16:    */ import com.asinfo.as2.utils.JsfUtil;
/*  17:    */ import java.math.BigDecimal;
/*  18:    */ import java.util.Date;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class PrestamoAprobacionBean
/*  27:    */   extends PrestamoBean
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30: 43 */   private boolean aprobar = false;
/*  31: 44 */   private boolean contabilizar = false;
/*  32:    */   private BigDecimal totalPrestamo;
/*  33:    */   boolean modificado;
/*  34:    */   
/*  35:    */   public String crear()
/*  36:    */   {
/*  37: 50 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  38: 51 */     return "";
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void dialogoContabilizar()
/*  42:    */   {
/*  43: 58 */     setIndicadorContabilizar(true);
/*  44: 59 */     setPrestamo((Prestamo)getDtPrestamo().getRowData());
/*  45: 60 */     setPrestamo(this.servicioPrestamo.cargarDetalle(getPrestamo().getId()));
/*  46: 61 */     getPrestamo().setBeneficiario(getPrestamo().getEmpleado().getNombres() + " " + getPrestamo().getEmpleado().getApellidos());
/*  47: 62 */     getPrestamo().setFechaContabilizacion(new Date());
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String guardar()
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 67 */       this.servicioPrestamo.guardar(getPrestamo(), false, this.modificado);
/*  55: 68 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  56: 69 */       setEditado(false);
/*  57: 70 */       limpiar();
/*  58:    */     }
/*  59:    */     catch (AS2Exception e)
/*  60:    */     {
/*  61: 72 */       JsfUtil.addErrorMessage(e, "");
/*  62: 73 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/*  63: 74 */       e.printStackTrace();
/*  64:    */     }
/*  65:    */     catch (Exception e)
/*  66:    */     {
/*  67: 76 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  68: 77 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  69:    */     }
/*  70: 79 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String limpiar()
/*  74:    */   {
/*  75: 83 */     this.modificado = false;
/*  76: 84 */     return super.limpiar();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void contabilizarPrestamo()
/*  80:    */   {
/*  81: 90 */     setContabilizar(false);
/*  82:    */     try
/*  83:    */     {
/*  84: 92 */       if ((getPrestamo().getEstado() == Estado.APROBADO) && (getPrestamo().getTipoPrestamo().isIndicadorContabilizar()))
/*  85:    */       {
/*  86: 93 */         this.servicioPrestamo.guardar(getPrestamo(), false, false);
/*  87:    */         
/*  88: 95 */         this.servicioPrestamo.contabilizar(getPrestamo());
/*  89: 96 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  90:    */       }
/*  91:    */     }
/*  92:    */     catch (ExcepcionAS2Financiero e)
/*  93:    */     {
/*  94: 99 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  95:100 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  96:    */     }
/*  97:    */     catch (ExcepcionAS2 e)
/*  98:    */     {
/*  99:102 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 100:103 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 101:    */     }
/* 102:    */     catch (Exception e)
/* 103:    */     {
/* 104:105 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 105:106 */       e.printStackTrace();
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void dialogoAprobacion()
/* 110:    */   {
/* 111:115 */     setPrestamo((Prestamo)getDtPrestamo().getRowData());
/* 112:116 */     setPrestamo(this.servicioPrestamo.cargarDetalle(getPrestamo().getId()));
/* 113:117 */     getPrestamo().setFechaAprobacion(new Date());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void calcularTotalPrestamo()
/* 117:    */   {
/* 118:121 */     this.totalPrestamo = BigDecimal.ZERO;
/* 119:122 */     for (DetallePrestamo dp : getPrestamo().getListaDetallePrestamo()) {
/* 120:123 */       this.totalPrestamo = this.totalPrestamo.add(dp.getCuota());
/* 121:    */     }
/* 122:125 */     getPrestamo().setTotalCuota(this.totalPrestamo);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void aprobarPrestamo()
/* 126:    */   {
/* 127:132 */     setAprobar(false);
/* 128:    */     try
/* 129:    */     {
/* 130:136 */       if ((getPrestamo().getFechaInicioDescuento() != null) && (getPrestamo().getFechaAprobacion() != null))
/* 131:    */       {
/* 132:137 */         getPrestamo().setEstado(Estado.APROBADO);
/* 133:138 */         this.servicioPrestamo.guardar(getPrestamo(), false, false);
/* 134:139 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 135:    */       }
/* 136:    */       else
/* 137:    */       {
/* 138:141 */         addErrorMessage(getLanguageController().getMensaje("msg_error_fecha"));
/* 139:    */       }
/* 140:    */     }
/* 141:    */     catch (ExcepcionAS2Financiero e)
/* 142:    */     {
/* 143:145 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 144:146 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 145:    */     }
/* 146:    */     catch (ExcepcionAS2 e)
/* 147:    */     {
/* 148:148 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 149:149 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 150:    */     }
/* 151:    */     catch (Exception e)
/* 152:    */     {
/* 153:151 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 154:152 */       e.printStackTrace();
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void anularAsiento()
/* 159:    */   {
/* 160:    */     try
/* 161:    */     {
/* 162:164 */       this.servicioPrestamo.anularAsiento(getPrestamo());
/* 163:165 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 164:    */     }
/* 165:    */     catch (AS2Exception e)
/* 166:    */     {
/* 167:167 */       JsfUtil.addErrorMessage(e, "");
/* 168:    */     }
/* 169:    */     catch (ExcepcionAS2Financiero e)
/* 170:    */     {
/* 171:169 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 172:170 */       e.printStackTrace();
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void actualizaFechaDescuento()
/* 177:    */   {
/* 178:176 */     DetallePrestamo detallePrestamo = (DetallePrestamo)getDtDetallePrestamo().getRowData();
/* 179:177 */     for (DetallePagoCuotaPrestamo detallePagoCuotaPrestamo : detallePrestamo.getListaDetallePagoCuotaPrestamo())
/* 180:    */     {
/* 181:178 */       this.modificado = true;
/* 182:179 */       detallePagoCuotaPrestamo.setEliminado(true);
/* 183:180 */       detallePagoCuotaPrestamo.getPagoRolEmpleado().getPagoRol().setIndicadorReprocesar(Boolean.valueOf(true));
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String editar()
/* 188:    */   {
/* 189:188 */     if (getPrestamo().getIdPrestamo() != 0) {
/* 190:    */       try
/* 191:    */       {
/* 192:191 */         setPrestamo(this.servicioPrestamo.cargarDetalle(getPrestamo().getId()));
/* 193:192 */         this.servicioPrestamo.validar(getPrestamo());
/* 194:193 */         setEmpleado(getPrestamo().getEmpleado());
/* 195:    */         
/* 196:195 */         setMontoPrestamo(getPrestamo().getMonto());
/* 197:196 */         setEditado(true);
/* 198:    */       }
/* 199:    */       catch (AS2Exception e)
/* 200:    */       {
/* 201:198 */         JsfUtil.addErrorMessage(e, "");
/* 202:199 */         e.printStackTrace();
/* 203:    */       }
/* 204:    */       catch (ExcepcionAS2Financiero e)
/* 205:    */       {
/* 206:201 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 207:    */       }
/* 208:    */     } else {
/* 209:205 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 210:    */     }
/* 211:207 */     return "";
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean isAprobar()
/* 215:    */   {
/* 216:212 */     return this.aprobar;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setAprobar(boolean aprobar)
/* 220:    */   {
/* 221:216 */     this.aprobar = aprobar;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public boolean isContabilizar()
/* 225:    */   {
/* 226:220 */     return this.contabilizar;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setContabilizar(boolean contabilizar)
/* 230:    */   {
/* 231:224 */     this.contabilizar = contabilizar;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public BigDecimal getTotalPrestamo()
/* 235:    */   {
/* 236:228 */     return this.totalPrestamo;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setTotalPrestamo(BigDecimal totalPrestamo)
/* 240:    */   {
/* 241:232 */     this.totalPrestamo = totalPrestamo;
/* 242:    */   }
/* 243:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.PrestamoAprobacionBean
 * JD-Core Version:    0.7.0.1
 */