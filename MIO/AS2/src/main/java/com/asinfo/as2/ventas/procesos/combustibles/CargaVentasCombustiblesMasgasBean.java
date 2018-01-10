/*   1:    */ package com.asinfo.as2.ventas.procesos.combustibles;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.combustibles.ServicioCargaProcesoCombustibles;
/*   4:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageController;
/*   7:    */ import com.asinfo.as2.entities.Banco;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  12:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  13:    */ import com.asinfo.as2.utils.encriptacion.ExcepcionAS2Encriptacion;
/*  14:    */ import java.io.BufferedInputStream;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.io.InputStream;
/*  17:    */ import java.util.List;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.event.FileUploadEvent;
/*  23:    */ import org.primefaces.model.UploadedFile;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class CargaVentasCombustiblesMasgasBean
/*  28:    */   extends PageController
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   private Banco banco;
/*  32:    */   private List<Banco> listaBanco;
/*  33:    */   @EJB
/*  34:    */   private ServicioCargaProcesoCombustibles servicioCargaProcesoCombustibles;
/*  35:    */   @EJB
/*  36:    */   private ServicioGenerico<Banco> servicioBanco;
/*  37:    */   
/*  38:    */   public String cargarFacturasExcel(FileUploadEvent event)
/*  39:    */   {
/*  40: 66 */     cargarFacturas(event, false);
/*  41: 67 */     return null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String cargarFacturasEncriptado(FileUploadEvent event)
/*  45:    */   {
/*  46: 71 */     cargarFacturas(event, true);
/*  47: 72 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void cargarFacturas(FileUploadEvent event, boolean encriptado)
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 79 */       String fileName = event.getFile().getFileName();
/*  55: 80 */       InputStream imInputStream = new BufferedInputStream(event.getFile().getInputstream());
/*  56: 83 */       if ("42".equals(this.banco.getCodigo())) {
/*  57: 84 */         this.servicioCargaProcesoCombustibles.cargarFacturasBancoGeneralRuminahuiPichinchaMasgas(fileName, imInputStream, encriptado);
/*  58: 87 */       } else if ("17".equals(this.banco.getCodigo())) {
/*  59: 88 */         this.servicioCargaProcesoCombustibles.cargarFacturasBancoGuayaquilMasgas(fileName, imInputStream, encriptado);
/*  60: 91 */       } else if ("32".equals(this.banco.getCodigo())) {
/*  61: 92 */         this.servicioCargaProcesoCombustibles.cargarFacturasBancoInternacionalMasgas(fileName, imInputStream, Estado.PROCESADO, encriptado);
/*  62:    */       }
/*  63: 95 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  64:    */     }
/*  65:    */     catch (ExcepcionAS2Encriptacion e)
/*  66:    */     {
/*  67: 98 */       addErrorMessage(e.getMessage());
/*  68: 99 */       LOG.error("ERROR AL CARGA DATOS BANCO", e);
/*  69:    */     }
/*  70:    */     catch (ExcepcionAS2Financiero e)
/*  71:    */     {
/*  72:102 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  73:103 */       LOG.error("ERROR AL CARGA DATOS BANCO", e);
/*  74:    */     }
/*  75:    */     catch (ExcepcionAS2Compras e)
/*  76:    */     {
/*  77:106 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  78:107 */       LOG.error("ERROR AL CARGA DATOS BANCO", e);
/*  79:    */     }
/*  80:    */     catch (ExcepcionAS2 e)
/*  81:    */     {
/*  82:110 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  83:111 */       LOG.error("ERROR AL CARGA DATOS BANCO", e);
/*  84:    */     }
/*  85:    */     catch (AS2Exception e)
/*  86:    */     {
/*  87:114 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  88:115 */       LOG.error("ERROR AL CARGA DATOS BANCO", e);
/*  89:    */     }
/*  90:    */     catch (IOException e)
/*  91:    */     {
/*  92:118 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  93:119 */       e.printStackTrace();
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List<Banco> getListaBanco()
/*  98:    */   {
/*  99:134 */     if (this.listaBanco == null) {
/* 100:135 */       this.listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, null);
/* 101:    */     }
/* 102:137 */     return this.listaBanco;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Banco getBanco()
/* 106:    */   {
/* 107:146 */     return this.banco;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setBanco(Banco banco)
/* 111:    */   {
/* 112:156 */     this.banco = banco;
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.combustibles.CargaVentasCombustiblesMasgasBean
 * JD-Core Version:    0.7.0.1
 */