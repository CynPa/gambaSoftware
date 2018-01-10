/*   1:    */ package com.asinfo.as2.nomina.asistencia.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.nomina.asistencia.Turno;
/*   8:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class TurnoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -6642782572366933215L;
/*  28:    */   @EJB
/*  29:    */   private ServicioGenerico<Turno> servicioTurno;
/*  30:    */   @EJB
/*  31:    */   private ServicioAsistenciaConfiguracion servicioAsistenciaConfiguracion;
/*  32:    */   private Turno turno;
/*  33:    */   private LazyDataModel<Turno> listaTurno;
/*  34:    */   private DataTable dtTurno;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 75 */     this.listaTurno = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = -1752987002238164010L;
/*  42:    */       
/*  43:    */       public List<Turno> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 85 */         List<Turno> lista = new ArrayList();
/*  46: 86 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 88 */         lista = TurnoBean.this.servicioTurno.obtenerListaPorPagina(Turno.class, startIndex, pageSize, sortField, ordenar, filters);
/*  49: 89 */         TurnoBean.this.listaTurno.setRowCount(TurnoBean.this.servicioTurno.contarPorCriterio(Turno.class, filters));
/*  50:    */         
/*  51: 91 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String crearTurno()
/*  57:    */   {
/*  58:104 */     this.turno = new Turno();
/*  59:105 */     this.turno.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60:106 */     this.turno.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:107 */     return "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String limpiar()
/*  65:    */   {
/*  66:116 */     crearTurno();
/*  67:117 */     return "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String editar()
/*  71:    */   {
/*  72:126 */     if ((getTurno() != null) && (getTurno().getId() != 0)) {
/*  73:127 */       setEditado(true);
/*  74:    */     } else {
/*  75:129 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  76:    */     }
/*  77:131 */     return "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String guardar()
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:141 */       this.servicioTurno.guardar(getTurno());
/*  85:142 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  86:143 */       limpiar();
/*  87:144 */       setEditado(false);
/*  88:    */     }
/*  89:    */     catch (Exception e)
/*  90:    */     {
/*  91:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  92:147 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  93:    */     }
/*  94:149 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String eliminar()
/*  98:    */   {
/*  99:158 */     if ((getTurno() != null) && (getTurno().getId() != 0)) {
/* 100:    */       try
/* 101:    */       {
/* 102:160 */         this.servicioTurno.eliminar(getTurno());
/* 103:161 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 104:    */       }
/* 105:    */       catch (Exception e)
/* 106:    */       {
/* 107:163 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 108:164 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 109:    */       }
/* 110:    */     } else {
/* 111:167 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 112:    */     }
/* 113:169 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String cargarDatos()
/* 117:    */   {
/* 118:178 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Turno getTurno()
/* 122:    */   {
/* 123:186 */     return this.turno;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setTurno(Turno turno)
/* 127:    */   {
/* 128:190 */     this.turno = turno;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public LazyDataModel<Turno> getListaTurno()
/* 132:    */   {
/* 133:194 */     return this.listaTurno;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setListaTurno(LazyDataModel<Turno> listaTurno)
/* 137:    */   {
/* 138:198 */     this.listaTurno = listaTurno;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public DataTable getDtTurno()
/* 142:    */   {
/* 143:202 */     return this.dtTurno;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setDtTurno(DataTable dtTurno)
/* 147:    */   {
/* 148:206 */     this.dtTurno = dtTurno;
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.configuracion.TurnoBean
 * JD-Core Version:    0.7.0.1
 */