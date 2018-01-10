/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   4:    */ import com.asinfo.as2.entities.LecturaBalanza;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*   7:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   8:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*   9:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.primefaces.component.datatable.DataTable;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @ViewScoped
/*  21:    */ public class RecepcionDespachoMaterialesBean
/*  22:    */   extends PageControllerAS2
/*  23:    */ {
/*  24:    */   @EJB
/*  25:    */   private ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  26:    */   private static final long serialVersionUID = -5463907651730263568L;
/*  27:    */   private List<LecturaBalanza> listaLecturaBalanza;
/*  28:    */   private DataTable dtLecturaBalanza;
/*  29:    */   
/*  30:    */   @PostConstruct
/*  31:    */   public void init()
/*  32:    */   {
/*  33: 51 */     this.listaLecturaBalanza = null;
/*  34: 52 */     getListaLecturaBalanza();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List<LecturaBalanza> getListaLecturaBalanza()
/*  38:    */   {
/*  39: 56 */     if (this.listaLecturaBalanza == null)
/*  40:    */     {
/*  41: 57 */       Usuario usuarioSesion = AppUtil.getUsuarioEnSesion();
/*  42: 58 */       List<Integer> idsSucursalesAsignadasUsuarioEnSesion = null;
/*  43: 62 */       if ((TipoVisualizacionEnum.MIS_SUCURSALES.equals(usuarioSesion.getTipoVisualizacion())) || 
/*  44: 63 */         (TipoVisualizacionEnum.MIS_REGISTROS.equals(usuarioSesion.getTipoVisualizacion()))) {
/*  45: 64 */         idsSucursalesAsignadasUsuarioEnSesion = getListaIdsSucursalesAsignadasUsuarioEnSesion(usuarioSesion);
/*  46:    */       }
/*  47: 70 */       this.listaLecturaBalanza = this.servicioOrdenSalidaMaterial.obtenerPesadasNoRecibidas(AppUtil.getOrganizacion().getId(), null, idsSucursalesAsignadasUsuarioEnSesion);
/*  48:    */     }
/*  49: 73 */     return this.listaLecturaBalanza;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setListaLecturaBalanza(List<LecturaBalanza> listaLecturaBalanza)
/*  53:    */   {
/*  54: 77 */     this.listaLecturaBalanza = listaLecturaBalanza;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void recepcionarDespachoMateriales(LecturaBalanza lecturaBalanza)
/*  58:    */   {
/*  59:    */     try
/*  60:    */     {
/*  61: 82 */       this.servicioOrdenSalidaMaterial.recibirLecturaBalanza(lecturaBalanza);
/*  62: 83 */       this.dtLecturaBalanza.reset();
/*  63:    */     }
/*  64:    */     catch (AS2Exception e)
/*  65:    */     {
/*  66: 85 */       JsfUtil.addErrorMessage(e, "");
/*  67:    */     }
/*  68: 87 */     this.listaLecturaBalanza = null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public DataTable getDtLecturaBalanza()
/*  72:    */   {
/*  73: 91 */     return this.dtLecturaBalanza;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setDtLecturaBalanza(DataTable dtLecturaBalanza)
/*  77:    */   {
/*  78: 95 */     this.dtLecturaBalanza = dtLecturaBalanza;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String editar()
/*  82:    */   {
/*  83:101 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String guardar()
/*  87:    */   {
/*  88:107 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:113 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String limpiar()
/*  97:    */   {
/*  98:119 */     return null;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String cargarDatos()
/* 102:    */   {
/* 103:125 */     return null;
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.RecepcionDespachoMaterialesBean
 * JD-Core Version:    0.7.0.1
 */