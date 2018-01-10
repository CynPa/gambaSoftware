/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCargarDatosInicialesDesdeXML;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioUpdate;
/*   5:    */ import com.asinfo.as2.dao.ConfiguracionDao;
/*   6:    */ import com.asinfo.as2.dao.GenericoDao;
/*   7:    */ import com.asinfo.as2.dao.OrganizacionDao;
/*   8:    */ import com.asinfo.as2.dao.seguridad.SistemaDao;
/*   9:    */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*  10:    */ import com.asinfo.as2.entities.VersionSistema;
/*  11:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*  14:    */ import com.asinfo.as2.utils.ArchivoUtil;
/*  15:    */ import java.io.PrintStream;
/*  16:    */ import java.sql.SQLException;
/*  17:    */ import java.util.List;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.annotation.Resource;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.ejb.SessionContext;
/*  22:    */ import javax.ejb.Singleton;
/*  23:    */ import javax.ejb.Startup;
/*  24:    */ import javax.ejb.TransactionManagement;
/*  25:    */ import javax.ejb.TransactionManagementType;
/*  26:    */ import javax.transaction.UserTransaction;
/*  27:    */ import org.jdom2.Document;
/*  28:    */ import org.jdom2.Element;
/*  29:    */ 
/*  30:    */ @Singleton
/*  31:    */ @Startup
/*  32:    */ @TransactionManagement(TransactionManagementType.BEAN)
/*  33:    */ public class ServicioUpdateImpl
/*  34:    */   implements ServicioUpdate
/*  35:    */ {
/*  36:    */   @Resource
/*  37:    */   private UserTransaction userTransaction;
/*  38:    */   @Resource
/*  39:    */   protected SessionContext context;
/*  40:    */   @EJB
/*  41:    */   private ConfiguracionDao configuracionDao;
/*  42:    */   @EJB
/*  43:    */   private OrganizacionDao organizacionDao;
/*  44:    */   @EJB
/*  45:    */   private UsuarioDao usuarioDao;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioCargarDatosInicialesDesdeXML servicioCargarDatosInicialesDesdeXML;
/*  48:    */   @EJB
/*  49:    */   private transient ServicioEnvioEmail servicioEnvioEmail;
/*  50:    */   @EJB
/*  51:    */   protected transient GenericoDao<VersionSistema> versionSistemaDao;
/*  52:    */   @EJB
/*  53:    */   SistemaDao sistemaDao;
/*  54:    */   private static final String SQLServer2008Dialect = "org.hibernate.dialect.SQLServer2008Dialect";
/*  55:    */   private static final String SQLServerDialect = "org.hibernate.dialect.SQLServerDialect";
/*  56:    */   private static final String PostgreSQLDialect = "org.hibernate.dialect.PostgreSQLDialect";
/*  57:    */   
/*  58:    */   @PostConstruct
/*  59:    */   public void cargarUpdate()
/*  60:    */     throws SQLException
/*  61:    */   {
/*  62: 84 */     EntidadSistema entidadSistema = this.sistemaDao.buscarPorNombre("AS2-ERP");
/*  63: 85 */     if (entidadSistema != null)
/*  64:    */     {
/*  65: 86 */       String hibernatedialectDB = this.configuracionDao.obtenerPropiedadesPersistence();
/*  66: 87 */       ServicioCargarDatosInicialesDesdeXMLBase scdi = new ServicioCargarDatosInicialesDesdeXMLBase();
/*  67: 88 */       String archivoBaseXML = "";
/*  68: 89 */       if ((hibernatedialectDB.equals("org.hibernate.dialect.SQLServerDialect")) || (hibernatedialectDB.equals("org.hibernate.dialect.SQLServer2008Dialect"))) {
/*  69: 90 */         archivoBaseXML = "updateSQL_base.xml";
/*  70: 91 */       } else if (hibernatedialectDB.equals("org.hibernate.dialect.PostgreSQLDialect")) {
/*  71: 92 */         archivoBaseXML = "updatePSQL_base.xml";
/*  72:    */       }
/*  73: 94 */       String rutaArchivo = scdi.getPathResoucesAS2("datos_iniciales") + archivoBaseXML;
/*  74: 95 */       Document document = null;
/*  75:    */       try
/*  76:    */       {
/*  77: 97 */         document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  78: 98 */         Element nodoPrincipal = document.getRootElement();
/*  79: 99 */         List<Element> listaVersiones = nodoPrincipal.getChildren("version");
/*  80:100 */         for (Element version : listaVersiones) {
/*  81:101 */           if ((this.sistemaDao.obtenerVersionSistema().equals(version.getAttributeValue("id").trim())) || (version.getAttributeValue("id").trim().equals("0")))
/*  82:    */           {
/*  83:102 */             System.out.println("===================== UPDATE VERSION: " + version.getAttributeValue("id") + "  ==================");
/*  84:103 */             List<Element> listaSqls = version.getChildren();
/*  85:104 */             ejecutarUpdateBloque(listaSqls, entidadSistema.getIdSistema());
/*  86:105 */             this.servicioCargarDatosInicialesDesdeXML.updateReportes(version.getAttributeValue("id").toString());
/*  87:    */           }
/*  88:    */         }
/*  89:    */       }
/*  90:    */       catch (ExcepcionAS2 e)
/*  91:    */       {
/*  92:109 */         e.printStackTrace();
/*  93:    */       }
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void ejecutarUpdateBloque(List<Element> listaSqls, int idSistema)
/*  98:    */   {
/*  99:115 */     String sqlEjecutado = "";
/* 100:    */     try
/* 101:    */     {
/* 102:118 */       this.userTransaction.begin();
/* 103:119 */       for (Element sql : listaSqls)
/* 104:    */       {
/* 105:120 */         sqlEjecutado = sql.getText().replaceAll("\n", "  ").replace("@idSistema", "" + idSistema);
/* 106:121 */         this.configuracionDao.execute(sqlEjecutado);
/* 107:122 */         this.configuracionDao.flush();
/* 108:    */       }
/* 109:124 */       this.userTransaction.commit();
/* 110:    */     }
/* 111:    */     catch (Exception e)
/* 112:    */     {
/* 113:126 */       System.out.println("======================================= UPDATE SQL ERROR =============================================== \n " + sqlEjecutado);
/* 114:127 */       System.out.println("======================================================================================================== ");
/* 115:128 */       e.printStackTrace();
/* 116:    */     }
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioUpdateImpl
 * JD-Core Version:    0.7.0.1
 */