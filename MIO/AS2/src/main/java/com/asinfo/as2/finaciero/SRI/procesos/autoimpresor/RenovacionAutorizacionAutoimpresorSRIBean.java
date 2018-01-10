/*   1:    */ package com.asinfo.as2.finaciero.SRI.procesos.autoimpresor;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.sri.AutorizacionAutoimpresorSRI;
/*   7:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoAutoimpresorSRI;
/*   8:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*   9:    */ import com.asinfo.as2.entities.sri.AutorizacionPuntoDeVentaAutoimpresorSRI;
/*  10:    */ import com.asinfo.as2.enumeraciones.ProcesoAutoimpresorSRIEnum;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*  13:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.List;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class RenovacionAutorizacionAutoimpresorSRIBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 8782410859351300688L;
/*  30:    */   @EJB
/*  31:    */   private ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  32:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 76 */     crearEntidad();
/*  38: 77 */     setEditado(true);
/*  39:    */   }
/*  40:    */   
/*  41:    */   private void crearEntidad()
/*  42:    */   {
/*  43:    */     try
/*  44:    */     {
/*  45: 91 */       AutorizacionAutoimpresorSRI autorizacionAnterior = this.servicioAutorizacionAutoimpresorSRI.obtenerAutorizacionSRIVigente(AppUtil.getOrganizacion().getIdOrganizacion());
/*  46: 92 */       long diasEntreFechas = FuncionesUtiles.DiasEntreFechas(new Date(), autorizacionAnterior.getFechaHasta());
/*  47: 93 */       if ((diasEntreFechas > 0L) && (diasEntreFechas < 31L))
/*  48:    */       {
/*  49: 95 */         this.autorizacionAutoimpresorSRI = new AutorizacionAutoimpresorSRI();
/*  50: 96 */         this.autorizacionAutoimpresorSRI.setAutorizacionAutoimpresorSRIAnterior(autorizacionAnterior);
/*  51: 97 */         this.autorizacionAutoimpresorSRI.setIdOrganizacion(autorizacionAnterior.getIdOrganizacion());
/*  52: 98 */         this.autorizacionAutoimpresorSRI.setIdSucursal(autorizacionAnterior.getIdSucursal());
/*  53: 99 */         this.autorizacionAutoimpresorSRI.setActivo(true);
/*  54:100 */         for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpv : autorizacionAnterior
/*  55:101 */           .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()) {
/*  56:102 */           if (adpv.isActivo())
/*  57:    */           {
/*  58:103 */             AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpvN = new AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI();
/*  59:104 */             adpvN.setActivo(true);
/*  60:105 */             adpvN.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/*  61:106 */             adpvN.setDocumentoBase(adpv.getDocumentoBase());
/*  62:107 */             adpvN.setPuntoDeVenta(adpv.getPuntoDeVenta());
/*  63:108 */             adpvN.setNumero(adpv.getNumero());
/*  64:109 */             adpvN.setIndicadorNuevo(false);
/*  65:110 */             adpvN.setFechaExclusion(null);
/*  66:111 */             adpvN.setFechaInclusion(null);
/*  67:112 */             adpvN.setNumeroAnterior(adpv.getNumero() - 1);
/*  68:113 */             adpvN.setNumeroInicial(adpv.getNumero());
/*  69:114 */             this.autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI().add(adpvN);
/*  70:    */           }
/*  71:    */         }
/*  72:118 */         for (AutorizacionDocumentoAutoimpresorSRI adpv : autorizacionAnterior.getListaAutorizacionDocumentoAutoimpresorSRI())
/*  73:    */         {
/*  74:119 */           AutorizacionDocumentoAutoimpresorSRI adpvN = new AutorizacionDocumentoAutoimpresorSRI();
/*  75:120 */           adpvN.setActivo(adpv.isActivo());
/*  76:121 */           adpvN.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/*  77:122 */           adpvN.setDocumentoBase(adpv.getDocumentoBase());
/*  78:123 */           this.autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoAutoimpresorSRI().add(adpvN);
/*  79:    */         }
/*  80:126 */         for (AutorizacionPuntoDeVentaAutoimpresorSRI adpv : autorizacionAnterior.getListaAutorizacionPuntoDeVentaAutoimpresorSRI())
/*  81:    */         {
/*  82:127 */           AutorizacionPuntoDeVentaAutoimpresorSRI adpvN = new AutorizacionPuntoDeVentaAutoimpresorSRI();
/*  83:128 */           adpvN.setActivo(adpv.isActivo());
/*  84:129 */           adpvN.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/*  85:130 */           adpvN.setPuntoDeVenta(adpv.getPuntoDeVenta());
/*  86:131 */           this.autorizacionAutoimpresorSRI.getListaAutorizacionPuntoDeVentaAutoimpresorSRI().add(adpvN);
/*  87:    */         }
/*  88:    */       }
/*  89:    */       else
/*  90:    */       {
/*  91:134 */         throw new ExcepcionAS2Financiero("msg_error_fecha_30_dias_caducidad");
/*  92:    */       }
/*  93:    */     }
/*  94:    */     catch (ExcepcionAS2Financiero e)
/*  95:    */     {
/*  96:138 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String editar()
/* 101:    */   {
/* 102:149 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 103:150 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String guardar()
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:160 */       AutorizacionAutoimpresorSRI autorizacionAnterior = this.servicioAutorizacionAutoimpresorSRI.obtenerAutorizacionSRIVigente(
/* 111:161 */         AppUtil.getOrganizacion().getIdOrganizacion());
/* 112:162 */       long diasEntreFechas = FuncionesUtiles.DiasEntreFechas(autorizacionAnterior.getFechaHasta(), new Date());
/* 113:163 */       if ((diasEntreFechas > 0L) && (diasEntreFechas < 31L))
/* 114:    */       {
/* 115:165 */         addErrorMessage(getLanguageController().getMensaje("msg_error_fecha_cambio_software"));
/* 116:    */       }
/* 117:    */       else
/* 118:    */       {
/* 119:167 */         this.servicioAutorizacionAutoimpresorSRI.guardar(this.autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum.CAMBIO_SOFTWARE, this.autorizacionAutoimpresorSRI
/* 120:168 */           .getFechaDesde());
/* 121:169 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 122:170 */         setEditado(false);
/* 123:171 */         limpiar();
/* 124:    */       }
/* 125:    */     }
/* 126:    */     catch (ExcepcionAS2 e)
/* 127:    */     {
/* 128:174 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 129:175 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 130:    */     }
/* 131:    */     catch (Exception e)
/* 132:    */     {
/* 133:177 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 134:178 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 135:    */     }
/* 136:180 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String eliminar()
/* 140:    */   {
/* 141:189 */     addErrorMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 142:190 */     return "";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String cargarDatos()
/* 146:    */   {
/* 147:199 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String limpiar()
/* 151:    */   {
/* 152:208 */     crearEntidad();
/* 153:209 */     return "";
/* 154:    */   }
/* 155:    */   
/* 156:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRI()
/* 157:    */   {
/* 158:226 */     return this.autorizacionAutoimpresorSRI;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setAutorizacionAutoimpresorSRI(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 162:    */   {
/* 163:236 */     this.autorizacionAutoimpresorSRI = autorizacionAutoimpresorSRI;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.autoimpresor.RenovacionAutorizacionAutoimpresorSRIBean
 * JD-Core Version:    0.7.0.1
 */