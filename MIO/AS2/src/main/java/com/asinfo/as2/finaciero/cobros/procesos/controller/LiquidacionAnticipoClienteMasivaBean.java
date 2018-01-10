/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   8:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   9:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoCliente;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*  19:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioLiquidacionAnticipoCliente;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.util.AppUtil;
/*  22:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.List;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ import org.primefaces.event.SelectEvent;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @ViewScoped
/*  35:    */ public class LiquidacionAnticipoClienteMasivaBean
/*  36:    */   extends PageControllerAS2
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @EJB
/*  40:    */   private ServicioEmpresa servicioEmpresa;
/*  41:    */   @EJB
/*  42:    */   private ServicioAnticipoCliente servicioAnticipoCliente;
/*  43:    */   @EJB
/*  44:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  45:    */   @EJB
/*  46:    */   private ServicioDocumento servicioDocumento;
/*  47:    */   @EJB
/*  48:    */   private ServicioLiquidacionAnticipoCliente servicioLiquidacionAnticipoCliente;
/*  49:    */   private Empresa empresa;
/*  50:    */   private BigDecimal valor;
/*  51:    */   private List<Documento> listaDocumento;
/*  52:    */   
/*  53:    */   public String editar()
/*  54:    */   {
/*  55: 78 */     return "";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String guardar()
/*  59:    */   {
/*  60: 88 */     return "";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String eliminar()
/*  64:    */   {
/*  65: 98 */     return "";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String limpiar()
/*  69:    */   {
/*  70:108 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String cargarDatos()
/*  74:    */   {
/*  75:118 */     return "";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String crearAnticipoCliente()
/*  79:    */   {
/*  80:123 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void anularAnticipoCliente() {}
/*  84:    */   
/*  85:    */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event) {}
/*  86:    */   
/*  87:    */   public void onRowSelect(SelectEvent event) {}
/*  88:    */   
/*  89:    */   public List<Empresa> autocompletarClientes(String consulta)
/*  90:    */   {
/*  91:152 */     return this.servicioEmpresa.autocompletarClientes(consulta, true);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void procesar()
/*  95:    */   {
/*  96:157 */     List<AnticipoCliente> listaAnticiposClientes = this.servicioAnticipoCliente.listaAnticiposClientesMasivos(getValor(), AppUtil.getOrganizacion()
/*  97:158 */       .getIdOrganizacion(), getEmpresa());
/*  98:160 */     for (AnticipoCliente ac : listaAnticiposClientes)
/*  99:    */     {
/* 100:162 */       LiquidacionAnticipoCliente liquidacionAnticipoCliente = new LiquidacionAnticipoCliente();
/* 101:163 */       liquidacionAnticipoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 102:164 */       liquidacionAnticipoCliente.setIdSucursal(AppUtil.getSucursal().getId());
/* 103:165 */       liquidacionAnticipoCliente.setFecha(new Date());
/* 104:166 */       liquidacionAnticipoCliente.setEstado(Estado.ELABORADO);
/* 105:167 */       liquidacionAnticipoCliente.setAnticipoCliente(ac);
/* 106:168 */       liquidacionAnticipoCliente.setNumero("");
/* 107:170 */       if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 108:    */       {
/* 109:171 */         Documento documento = (Documento)getListaDocumento().get(0);
/* 110:172 */         liquidacionAnticipoCliente.setDocumento(documento);
/* 111:    */       }
/* 112:175 */       ac = this.servicioAnticipoCliente.cargarDetalle(ac.getIdAnticipoCliente());
/* 113:176 */       List<CuentaPorCobrar> listaFacturasPendientes = this.servicioFacturaCliente.obtenerFacturasPendientes(ac.getEmpresa().getIdEmpresa());
/* 114:177 */       BigDecimal valorPago = ac.getSaldo();
/* 115:179 */       if (listaFacturasPendientes != null)
/* 116:    */       {
/* 117:181 */         for (CuentaPorCobrar cxc : listaFacturasPendientes)
/* 118:    */         {
/* 119:182 */           DetalleLiquidacionAnticipoCliente detalleLiquidacionAnticipoCliente = new DetalleLiquidacionAnticipoCliente();
/* 120:183 */           detalleLiquidacionAnticipoCliente.setLiquidacionAnticipoCliente(liquidacionAnticipoCliente);
/* 121:184 */           detalleLiquidacionAnticipoCliente.setCuentaPorCobrar(cxc);
/* 122:    */           
/* 123:186 */           BigDecimal saldoCuentaPorCobra = cxc.getSaldo().subtract(cxc.getValorBloqueado());
/* 124:187 */           BigDecimal valor = BigDecimal.ZERO;
/* 125:189 */           if (valorPago.compareTo(saldoCuentaPorCobra) > 0) {
/* 126:190 */             valor = saldoCuentaPorCobra;
/* 127:    */           } else {
/* 128:192 */             valor = valorPago;
/* 129:    */           }
/* 130:195 */           detalleLiquidacionAnticipoCliente.setValor(valor);
/* 131:196 */           valorPago = valorPago.subtract(valor);
/* 132:    */           
/* 133:198 */           liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente().add(detalleLiquidacionAnticipoCliente);
/* 134:    */         }
/* 135:    */         try
/* 136:    */         {
/* 137:203 */           this.servicioLiquidacionAnticipoCliente.guardar(liquidacionAnticipoCliente);
/* 138:204 */           addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 139:    */         }
/* 140:    */         catch (ExcepcionAS2Financiero e)
/* 141:    */         {
/* 142:206 */           addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 143:207 */           LOG.error("ERROR AL GUARDAR DATOS", e);
/* 144:    */         }
/* 145:    */         catch (ExcepcionAS2 e)
/* 146:    */         {
/* 147:209 */           addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 148:210 */           LOG.info("ERROR AL GUARDAR DATOS", e);
/* 149:    */         }
/* 150:    */         catch (Exception e)
/* 151:    */         {
/* 152:212 */           addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 153:213 */           LOG.error("ERROR AL GUARDAR DATOS", e);
/* 154:    */         }
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public List<Documento> getListaDocumento()
/* 160:    */   {
/* 161:223 */     if (this.listaDocumento == null) {
/* 162:    */       try
/* 163:    */       {
/* 164:225 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.LIQUIDACION_ANTICIPO_CLIENTE, 
/* 165:226 */           AppUtil.getOrganizacion().getIdOrganizacion());
/* 166:    */       }
/* 167:    */       catch (ExcepcionAS2 e)
/* 168:    */       {
/* 169:228 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 170:    */       }
/* 171:    */     }
/* 172:231 */     return this.listaDocumento;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Empresa getEmpresa()
/* 176:    */   {
/* 177:235 */     return this.empresa;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setEmpresa(Empresa empresa)
/* 181:    */   {
/* 182:239 */     this.empresa = empresa;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public BigDecimal getValor()
/* 186:    */   {
/* 187:243 */     return this.valor;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setValor(BigDecimal valor)
/* 191:    */   {
/* 192:247 */     this.valor = valor;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 196:    */   {
/* 197:251 */     this.listaDocumento = listaDocumento;
/* 198:    */   }
/* 199:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.LiquidacionAnticipoClienteMasivaBean
 * JD-Core Version:    0.7.0.1
 */