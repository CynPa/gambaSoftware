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
/*  16:    */ import java.util.List;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class CambioSoftwareAutorizacionAutoimpresorSRIBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 8782410859351300688L;
/*  30:    */   @EJB
/*  31:    */   private ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  32:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI;
/*  33:    */   private DataTable dtAutorizacionAutoimpresorSRI;
/*  34:    */   
/*  35:    */   @PostConstruct
/*  36:    */   public void init()
/*  37:    */   {
/*  38: 76 */     crearEntidad();
/*  39: 77 */     setEditado(true);
/*  40:    */   }
/*  41:    */   
/*  42:    */   private void crearEntidad()
/*  43:    */   {
/*  44:    */     try
/*  45:    */     {
/*  46: 91 */       AutorizacionAutoimpresorSRI autorizacionAnterior = this.servicioAutorizacionAutoimpresorSRI.obtenerAutorizacionSRIVigente(AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 92 */       this.autorizacionAutoimpresorSRI = new AutorizacionAutoimpresorSRI();
/*  48: 93 */       this.autorizacionAutoimpresorSRI.setAutorizacionAutoimpresorSRIAnterior(autorizacionAnterior);
/*  49: 94 */       this.autorizacionAutoimpresorSRI.setIdOrganizacion(autorizacionAnterior.getIdOrganizacion());
/*  50: 95 */       this.autorizacionAutoimpresorSRI.setIdSucursal(autorizacionAnterior.getIdSucursal());
/*  51: 96 */       this.autorizacionAutoimpresorSRI.setActivo(true);
/*  52: 97 */       for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpv : autorizacionAnterior
/*  53: 98 */         .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()) {
/*  54: 99 */         if (adpv.isActivo())
/*  55:    */         {
/*  56:100 */           AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpvN = new AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI();
/*  57:101 */           adpvN.setActivo(true);
/*  58:102 */           adpvN.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/*  59:103 */           adpvN.setDocumentoBase(adpv.getDocumentoBase());
/*  60:104 */           adpvN.setPuntoDeVenta(adpv.getPuntoDeVenta());
/*  61:105 */           adpvN.setNumero(adpv.getNumero());
/*  62:106 */           adpvN.setIndicadorNuevo(false);
/*  63:107 */           adpvN.setFechaExclusion(null);
/*  64:108 */           adpvN.setFechaInclusion(null);
/*  65:109 */           adpvN.setNumeroAnterior(adpv.getNumero() - 1);
/*  66:110 */           adpvN.setNumeroInicial(adpv.getNumero());
/*  67:111 */           this.autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI().add(adpvN);
/*  68:    */         }
/*  69:    */       }
/*  70:115 */       for (AutorizacionDocumentoAutoimpresorSRI adpv : autorizacionAnterior.getListaAutorizacionDocumentoAutoimpresorSRI())
/*  71:    */       {
/*  72:116 */         AutorizacionDocumentoAutoimpresorSRI adpvN = new AutorizacionDocumentoAutoimpresorSRI();
/*  73:117 */         adpvN.setActivo(adpv.isActivo());
/*  74:118 */         adpvN.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/*  75:119 */         adpvN.setDocumentoBase(adpv.getDocumentoBase());
/*  76:120 */         this.autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoAutoimpresorSRI().add(adpvN);
/*  77:    */       }
/*  78:123 */       for (AutorizacionPuntoDeVentaAutoimpresorSRI adpv : autorizacionAnterior.getListaAutorizacionPuntoDeVentaAutoimpresorSRI())
/*  79:    */       {
/*  80:124 */         AutorizacionPuntoDeVentaAutoimpresorSRI adpvN = new AutorizacionPuntoDeVentaAutoimpresorSRI();
/*  81:125 */         adpvN.setActivo(adpv.isActivo());
/*  82:126 */         adpvN.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/*  83:127 */         adpvN.setPuntoDeVenta(adpv.getPuntoDeVenta());
/*  84:128 */         this.autorizacionAutoimpresorSRI.getListaAutorizacionPuntoDeVentaAutoimpresorSRI().add(adpvN);
/*  85:    */       }
/*  86:    */     }
/*  87:    */     catch (ExcepcionAS2Financiero e)
/*  88:    */     {
/*  89:132 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String editar()
/*  94:    */   {
/*  95:143 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  96:144 */     return "";
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String guardar()
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:154 */       if (FuncionesUtiles.DiasEntreFechas(this.autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior().getFechaDesde(), this.autorizacionAutoimpresorSRI
/* 104:155 */         .getFechaCambioSoftware()) < 1L)
/* 105:    */       {
/* 106:156 */         addErrorMessage(getLanguageController().getMensaje("msg_error_fecha_cambio_software"));
/* 107:    */       }
/* 108:    */       else
/* 109:    */       {
/* 110:158 */         this.autorizacionAutoimpresorSRI.setFechaDesde(this.autorizacionAutoimpresorSRI.getFechaCambioSoftware());
/* 111:159 */         this.servicioAutorizacionAutoimpresorSRI.guardar(this.autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum.CAMBIO_SOFTWARE, this.autorizacionAutoimpresorSRI
/* 112:160 */           .getFechaCambioSoftware());
/* 113:161 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 114:162 */         setEditado(false);
/* 115:163 */         limpiar();
/* 116:    */       }
/* 117:    */     }
/* 118:    */     catch (ExcepcionAS2 e)
/* 119:    */     {
/* 120:166 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 121:167 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 122:    */     }
/* 123:    */     catch (Exception e)
/* 124:    */     {
/* 125:169 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 126:170 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 127:    */     }
/* 128:172 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String eliminar()
/* 132:    */   {
/* 133:181 */     addErrorMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 134:182 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String cargarDatos()
/* 138:    */   {
/* 139:191 */     return "";
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String limpiar()
/* 143:    */   {
/* 144:200 */     crearEntidad();
/* 145:201 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRI()
/* 149:    */   {
/* 150:218 */     return this.autorizacionAutoimpresorSRI;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setAutorizacionAutoimpresorSRI(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 154:    */   {
/* 155:228 */     this.autorizacionAutoimpresorSRI = autorizacionAutoimpresorSRI;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public DataTable getDtAutorizacionAutoimpresorSRI()
/* 159:    */   {
/* 160:237 */     return this.dtAutorizacionAutoimpresorSRI;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setDtAutorizacionAutoimpresorSRI(DataTable dtAutorizacionAutoimpresorSRI)
/* 164:    */   {
/* 165:247 */     this.dtAutorizacionAutoimpresorSRI = dtAutorizacionAutoimpresorSRI;
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.autoimpresor.CambioSoftwareAutorizacionAutoimpresorSRIBean
 * JD-Core Version:    0.7.0.1
 */