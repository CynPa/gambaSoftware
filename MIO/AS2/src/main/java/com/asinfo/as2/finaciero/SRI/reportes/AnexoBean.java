/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageController;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   7:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  10:    */ import java.io.FileNotFoundException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Calendar;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ManagedProperty;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import javax.faces.model.SelectItem;
/*  18:    */ import javax.xml.parsers.ParserConfigurationException;
/*  19:    */ import javax.xml.transform.TransformerException;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.model.StreamedContent;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class AnexoBean
/*  26:    */   extends PageController
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -2750699381041705584L;
/*  29:    */   @ManagedProperty("#{anexoATSBean}")
/*  30:    */   private AnexoATSBean anexoATSBean;
/*  31:    */   @ManagedProperty("#{anexoREOCBean}")
/*  32:    */   private AnexoREOCBean anexoREOCBean;
/*  33:    */   private List<SelectItem> listaMes;
/*  34:    */   private StreamedContent file;
/*  35: 57 */   private int anio = Calendar.getInstance().get(1);
/*  36: 58 */   private int mes = Calendar.getInstance().get(2);
/*  37:    */   
/*  38:    */   public void generarAnexo()
/*  39:    */     throws ParserConfigurationException, TransformerException, FileNotFoundException, ExcepcionAS2Financiero
/*  40:    */   {
/*  41: 62 */     if (ParametrosSistema.getTipoAnexoSRI(AppUtil.getOrganizacion().getIdOrganizacion()).equals("ATS")) {
/*  42: 63 */       this.file = this.anexoATSBean.generarAnexo(this.anio, this.mes, AppUtil.getOrganizacion().getId());
/*  43:    */     } else {
/*  44: 65 */       this.file = this.anexoREOCBean.generarAnexo(this.anio, this.mes, AppUtil.getOrganizacion().getId());
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public AnexoATSBean getAnexoATSBean()
/*  49:    */   {
/*  50: 76 */     return this.anexoATSBean;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setAnexoATSBean(AnexoATSBean anexoATSBean)
/*  54:    */   {
/*  55: 86 */     this.anexoATSBean = anexoATSBean;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public AnexoREOCBean getAnexoREOCBean()
/*  59:    */   {
/*  60: 95 */     return this.anexoREOCBean;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setAnexoREOCBean(AnexoREOCBean anexoREOCBean)
/*  64:    */   {
/*  65:105 */     this.anexoREOCBean = anexoREOCBean;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<SelectItem> getListaMes()
/*  69:    */   {
/*  70:114 */     if (this.listaMes == null)
/*  71:    */     {
/*  72:115 */       this.listaMes = new ArrayList();
/*  73:116 */       for (Mes t : Mes.values())
/*  74:    */       {
/*  75:117 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/*  76:118 */         this.listaMes.add(item);
/*  77:    */       }
/*  78:    */     }
/*  79:121 */     return this.listaMes;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setListaMes(List<SelectItem> listaMes)
/*  83:    */   {
/*  84:131 */     this.listaMes = listaMes;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getAnio()
/*  88:    */   {
/*  89:140 */     return this.anio;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setAnio(int anio)
/*  93:    */   {
/*  94:150 */     this.anio = anio;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getMes()
/*  98:    */   {
/*  99:159 */     return this.mes;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setMes(int mes)
/* 103:    */   {
/* 104:169 */     this.mes = mes;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public StreamedContent getFile()
/* 108:    */   {
/* 109:    */     try
/* 110:    */     {
/* 111:179 */       generarAnexo();
/* 112:    */     }
/* 113:    */     catch (ParserConfigurationException pce)
/* 114:    */     {
/* 115:181 */       pce.printStackTrace();
/* 116:182 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 117:183 */       LOG.error("Error al generar el AT " + pce);
/* 118:    */     }
/* 119:    */     catch (TransformerException tfe)
/* 120:    */     {
/* 121:185 */       tfe.printStackTrace();
/* 122:186 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 123:187 */       LOG.error("Error al generar el AT " + tfe);
/* 124:    */     }
/* 125:    */     catch (FileNotFoundException e)
/* 126:    */     {
/* 127:189 */       e.printStackTrace();
/* 128:190 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado") + " " + e.getMessage());
/* 129:191 */       LOG.error("Archivo no encontrado " + e);
/* 130:    */     }
/* 131:    */     catch (ExcepcionAS2Financiero e)
/* 132:    */     {
/* 133:193 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 134:194 */       LOG.error("Archivo no encontrado " + e);
/* 135:    */     }
/* 136:    */     catch (Exception e)
/* 137:    */     {
/* 138:196 */       e.printStackTrace();
/* 139:197 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 140:198 */       LOG.error("Error al generar el AT " + e);
/* 141:    */     }
/* 142:201 */     return this.file;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.AnexoBean
 * JD-Core Version:    0.7.0.1
 */