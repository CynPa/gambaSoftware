/*   1:    */ package com.asinfo.as2.finaciero.SRI.procesos.autoimpresor;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ClaseFecha;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.sri.AutorizacionAutoimpresorSRI;
/*   8:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*   9:    */ import com.asinfo.as2.enumeraciones.ProcesoAutoimpresorSRIEnum;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.io.Serializable;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class AutorizacionXMLBean
/*  28:    */   extends PageController
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 6502107917491200540L;
/*  32:    */   @EJB
/*  33:    */   private transient ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  34:    */   private List<ProcesoAutoimpresorSRIEnum> listaProcesoAutoimpresorSRI;
/*  35:    */   private ProcesoAutoimpresorSRIEnum procesoAutoimpresorSRI;
/*  36:    */   private List<AutorizacionAutoimpresorSRI> listaAutorizacionAutoimpresorSRI;
/*  37:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI;
/*  38:    */   private List<ClaseFecha> listaFechaProceso;
/*  39:    */   private ClaseFecha fecha;
/*  40:    */   private List<AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> listaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  41: 56 */   private boolean indicadorFiltroFecha = false;
/*  42:    */   
/*  43:    */   public void obtenerFechaProceso()
/*  44:    */   {
/*  45: 59 */     int i = 0;
/*  46: 60 */     if ((this.autorizacionAutoimpresorSRI != null) && (this.procesoAutoimpresorSRI != null))
/*  47:    */     {
/*  48: 61 */       List<Date> listaFecha = this.servicioAutorizacionAutoimpresorSRI.obtenerFechaProceso(this.autorizacionAutoimpresorSRI, this.procesoAutoimpresorSRI);
/*  49: 62 */       this.listaFechaProceso = new ArrayList();
/*  50: 63 */       for (Date date : listaFecha)
/*  51:    */       {
/*  52: 64 */         ClaseFecha cf = new ClaseFecha(date);
/*  53: 65 */         cf.setIdClaseFecha(Integer.valueOf(i));
/*  54: 66 */         this.listaFechaProceso.add(cf);
/*  55: 67 */         i++;
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String generarArchivo()
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64: 74 */       this.servicioAutorizacionAutoimpresorSRI.generarXMLProceso(this.autorizacionAutoimpresorSRI, this.procesoAutoimpresorSRI, this.fecha.getFecha());
/*  65: 75 */       addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_generado"));
/*  66:    */     }
/*  67:    */     catch (ExcepcionAS2 e)
/*  68:    */     {
/*  69: 77 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  70: 78 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  71:    */     }
/*  72:    */     catch (Exception e)
/*  73:    */     {
/*  74: 80 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  75: 81 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  76:    */     }
/*  77: 83 */     return "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public ClaseFecha getFecha()
/*  81:    */   {
/*  82: 87 */     return this.fecha;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setFecha(ClaseFecha fecha)
/*  86:    */   {
/*  87: 91 */     System.out.println("entro en el setr de al fecha" + fecha.getFecha());
/*  88: 92 */     System.out.println("entro en el setr de al fecha" + fecha.getFechaLetras());
/*  89: 93 */     this.fecha = fecha;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List<ProcesoAutoimpresorSRIEnum> getListaProcesoAutoimpresorSRI()
/*  93:    */   {
/*  94: 97 */     if (this.listaProcesoAutoimpresorSRI == null)
/*  95:    */     {
/*  96: 98 */       this.listaProcesoAutoimpresorSRI = new ArrayList();
/*  97:100 */       for (ProcesoAutoimpresorSRIEnum t : ProcesoAutoimpresorSRIEnum.values()) {
/*  98:101 */         this.listaProcesoAutoimpresorSRI.add(t);
/*  99:    */       }
/* 100:    */     }
/* 101:104 */     return this.listaProcesoAutoimpresorSRI;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public ProcesoAutoimpresorSRIEnum getProcesoAutoimpresorSRI()
/* 105:    */   {
/* 106:108 */     return this.procesoAutoimpresorSRI;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setProcesoAutoimpresorSRI(ProcesoAutoimpresorSRIEnum procesoAutoimpresorSRI)
/* 110:    */   {
/* 111:112 */     setIndicadorFiltroFecha((procesoAutoimpresorSRI == ProcesoAutoimpresorSRIEnum.INCLUSION) || (procesoAutoimpresorSRI == ProcesoAutoimpresorSRIEnum.EXCLUSION));
/* 112:    */     
/* 113:114 */     this.procesoAutoimpresorSRI = procesoAutoimpresorSRI;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRI()
/* 117:    */   {
/* 118:118 */     return this.autorizacionAutoimpresorSRI;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setAutorizacionAutoimpresorSRI(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 122:    */   {
/* 123:122 */     this.autorizacionAutoimpresorSRI = autorizacionAutoimpresorSRI;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<ClaseFecha> getListaFechaProceso()
/* 127:    */   {
/* 128:126 */     return this.listaFechaProceso;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<AutorizacionAutoimpresorSRI> getListaAutorizacionAutoimpresorSRI()
/* 132:    */   {
/* 133:130 */     if (this.listaAutorizacionAutoimpresorSRI == null)
/* 134:    */     {
/* 135:131 */       Map<String, String> filters = new HashMap();
/* 136:132 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 137:133 */       this.listaAutorizacionAutoimpresorSRI = this.servicioAutorizacionAutoimpresorSRI.obtenerListaCombo("autorizacion", true, filters);
/* 138:    */     }
/* 139:135 */     return this.listaAutorizacionAutoimpresorSRI;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public List<AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()
/* 143:    */   {
/* 144:139 */     return this.listaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isIndicadorFiltroFecha()
/* 148:    */   {
/* 149:143 */     return this.indicadorFiltroFecha;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIndicadorFiltroFecha(boolean indicadorFiltroFecha)
/* 153:    */   {
/* 154:147 */     this.indicadorFiltroFecha = indicadorFiltroFecha;
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.autoimpresor.AutorizacionXMLBean
 * JD-Core Version:    0.7.0.1
 */