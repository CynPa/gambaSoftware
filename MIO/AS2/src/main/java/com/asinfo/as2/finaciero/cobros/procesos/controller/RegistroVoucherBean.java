/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.dao.DetalleFormaCobroDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   6:    */ import com.asinfo.as2.entities.Cobro;
/*   7:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*  12:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioRegistroVoucher;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.JsfUtil;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class RegistroVoucherBean
/*  33:    */   extends VoucherBean
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @EJB
/*  37:    */   private ServicioGenerico<DetalleFormaCobro> servicioVoucher;
/*  38: 52 */   private int i = 1;
/*  39:    */   
/*  40:    */   public DocumentoBase getDocumento()
/*  41:    */   {
/*  42: 56 */     return DocumentoBase.REGISTRO_VOUCHER;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public TipoCuentaBancariaOrganizacion getTipoCuentaBancariaOrganizacion()
/*  46:    */   {
/*  47: 61 */     return TipoCuentaBancariaOrganizacion.TARJETA;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String guardar()
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 67 */       this.servicioRegistroVoucher.guardarRegistroRegistro(this.cobro);
/*  55: 68 */       setEditado(false);
/*  56: 69 */       limpiar();
/*  57: 70 */       this.i = 1;
/*  58: 71 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  59:    */     }
/*  60:    */     catch (ExcepcionAS2Financiero e)
/*  61:    */     {
/*  62: 73 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  63:    */     }
/*  64:    */     catch (ExcepcionAS2 e)
/*  65:    */     {
/*  66: 75 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  67:    */     }
/*  68:    */     catch (AS2Exception e)
/*  69:    */     {
/*  70: 77 */       JsfUtil.addErrorMessage(e, "");
/*  71:    */     }
/*  72:    */     catch (Exception e)
/*  73:    */     {
/*  74: 79 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  75: 80 */       LOG.error("REGISTRO VOUCHERS-ERROR AL GUARDAR DATOS", e);
/*  76:    */     }
/*  77: 82 */     return "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void agregarDetalle()
/*  81:    */   {
/*  82: 87 */     HashMap<String, String> filters = new HashMap();
/*  83: 88 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  84: 89 */     filters.put("indicadorTarjetaCredito", "true");
/*  85: 90 */     DetalleFormaCobro dfc = new DetalleFormaCobro();
/*  86:    */     try
/*  87:    */     {
/*  88: 93 */       dfc.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  89: 94 */       dfc.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  90: 95 */       dfc.setPuntoVenta(AppUtil.getPuntoDeVenta());
/*  91: 96 */       dfc.setCobro(this.cobro);
/*  92:    */       
/*  93:    */ 
/*  94:    */ 
/*  95:100 */       dfc.setFormaPago(this.servicioFormaPago.buscarFormaPago(filters));
/*  96:    */       
/*  97:    */ 
/*  98:    */ 
/*  99:104 */       dfc.setSecuencial(this.detalleFormaCobroDao.obtenerSecuencialVoucher(AppUtil.getOrganizacion().getIdOrganizacion()) + this.i);
/* 100:    */       
/* 101:106 */       this.cobro.getListaDetalleFormaCobro().add(dfc);
/* 102:107 */       getListaDetalleVoucher().add(dfc);
/* 103:108 */       this.i += 1;
/* 104:    */     }
/* 105:    */     catch (ExcepcionAS2 e)
/* 106:    */     {
/* 107:111 */       e.printStackTrace();
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String eliminarVoucher(DetalleFormaCobro detalleFormaCobro)
/* 112:    */   {
/* 113:118 */     DetalleFormaCobro dfcBB = (DetalleFormaCobro)this.servicioVoucher.buscarPorId(DetalleFormaCobro.class, Integer.valueOf(detalleFormaCobro.getId()));
/* 114:120 */     if ((dfcBB != null) && (dfcBB.getCobroTarjeta() != null))
/* 115:    */     {
/* 116:122 */       detalleFormaCobro.setCobroTarjeta(dfcBB.getCobroTarjeta());
/* 117:123 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 118:    */     }
/* 119:    */     else
/* 120:    */     {
/* 121:125 */       detalleFormaCobro.setEliminado(true);
/* 122:126 */       super.setearTablas();
/* 123:    */     }
/* 124:128 */     this.i -= 1;
/* 125:129 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void totalizar(DetalleFormaCobro dfc)
/* 129:    */   {
/* 130:133 */     if (dfc.getFechaVoucher() != null)
/* 131:    */     {
/* 132:134 */       dfc.setValor(BigDecimal.ZERO);
/* 133:    */       
/* 134:136 */       BigDecimal montoIva = FuncionesUtiles.porcentaje(dfc.getBaseImponibleDiferenteCero(), 
/* 135:137 */         getPorcentajeIVA(AppUtil.getOrganizacion().getIdOrganizacion(), dfc.getFechaVoucher()));
/* 136:138 */       dfc.setMontoIva(montoIva);
/* 137:139 */       dfc.setValor(dfc.getBaseImponibleDiferenteCero().add(dfc.getBaseImponibleTarifaCero()).add(montoIva));
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void cargarPlanTarjetaListener(DetalleFormaCobro voucher)
/* 142:    */   {
/* 143:144 */     cargarPlanTarjeta(voucher.getTarjetaCredito());
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getI()
/* 147:    */   {
/* 148:148 */     return this.i;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setI(int i)
/* 152:    */   {
/* 153:152 */     this.i = i;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public BigDecimal getPorcentajeIVA(int idOrganizacion, Date fecha)
/* 157:    */   {
/* 158:156 */     return this.servicioImpuesto.getPorcentajeIVA(idOrganizacion, fecha);
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.RegistroVoucherBean
 * JD-Core Version:    0.7.0.1
 */