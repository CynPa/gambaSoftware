/*  1:   */ package com.asinfo.as2.finaciero.SRI.reportes;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.PageController;
/*  4:   */ import com.asinfo.as2.entities.Organizacion;
/*  5:   */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAnuladoSRI;
/*  6:   */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  7:   */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  8:   */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  9:   */ import com.asinfo.as2.util.AppUtil;
/* 10:   */ import com.asinfo.as2.utils.ParametrosSistema;
/* 11:   */ import java.io.File;
/* 12:   */ import java.io.FileNotFoundException;
/* 13:   */ import java.io.Serializable;
/* 14:   */ import javax.ejb.EJB;
/* 15:   */ import javax.xml.parsers.ParserConfigurationException;
/* 16:   */ import javax.xml.transform.TransformerException;
/* 17:   */ import org.primefaces.model.StreamedContent;
/* 18:   */ 
/* 19:   */ public abstract class AnexoBaseBean
/* 20:   */   extends PageController
/* 21:   */   implements Serializable
/* 22:   */ {
/* 23:   */   private static final long serialVersionUID = 6125115936933777027L;
/* 24:   */   @EJB
/* 25:   */   protected transient ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/* 26:   */   @EJB
/* 27:   */   protected transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/* 28:   */   @EJB
/* 29:   */   protected transient ServicioAnuladoSRI servicioAnuladoSRI;
/* 30:   */   protected static final String CARPETA_ANEXOS = "anexos";
/* 31:   */   protected static final String TIPO_CONTENIDO_ARCHIVO = "application/xml";
/* 32:   */   protected static final String FORMATO_FECHA = "dd/MM/yyyy";
/* 33:   */   
/* 34:   */   public abstract StreamedContent generarAnexo(int paramInt1, int paramInt2, int paramInt3)
/* 35:   */     throws ParserConfigurationException, TransformerException, FileNotFoundException, ExcepcionAS2Financiero;
/* 36:   */   
/* 37:   */   public String getDirectorioAnexos()
/* 38:   */   {
/* 39:65 */     String directorioAnexo = ParametrosSistema.getAS2_HOME(AppUtil.getOrganizacion().getIdOrganizacion()) + File.separator + "anexos";
/* 40:66 */     File directorio = new File(directorioAnexo);
/* 41:67 */     if (!directorio.exists()) {
/* 42:68 */       directorio.mkdirs();
/* 43:   */     }
/* 44:71 */     return directorioAnexo;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.AnexoBaseBean
 * JD-Core Version:    0.7.0.1
 */