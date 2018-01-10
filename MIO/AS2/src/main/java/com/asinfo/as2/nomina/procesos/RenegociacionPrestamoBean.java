/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.DetallePrestamo;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Prestamo;
/*   8:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPrestamo;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.annotation.PostConstruct;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.ViewScoped;
/*  15:    */ import org.apache.log4j.Logger;
/*  16:    */ 
/*  17:    */ @ManagedBean
/*  18:    */ @ViewScoped
/*  19:    */ public class RenegociacionPrestamoBean
/*  20:    */   extends PageControllerAS2
/*  21:    */ {
/*  22:    */   @EJB
/*  23:    */   private ServicioPrestamo servicioPrestamo;
/*  24:    */   private Prestamo prestamo;
/*  25:    */   private Empleado empleado;
/*  26: 55 */   List<Prestamo> listaPrestamo = new ArrayList();
/*  27:    */   
/*  28:    */   @PostConstruct
/*  29:    */   public void init() {}
/*  30:    */   
/*  31:    */   private void crearPrestamo()
/*  32:    */   {
/*  33: 78 */     this.prestamo = new Prestamo();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String editar()
/*  37:    */   {
/*  38: 88 */     if (getPrestamo().getIdPrestamo() > 0) {
/*  39: 89 */       setEditado(true);
/*  40:    */     } else {
/*  41: 91 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  42:    */     }
/*  43: 93 */     return "";
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String guardar()
/*  47:    */   {
/*  48:    */     try
/*  49:    */     {
/*  50:103 */       setEditado(false);
/*  51:104 */       limpiar();
/*  52:    */     }
/*  53:    */     catch (Exception e)
/*  54:    */     {
/*  55:106 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  56:107 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  57:    */     }
/*  58:109 */     return "";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String eliminar()
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65:120 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  66:    */     }
/*  67:    */     catch (Exception e)
/*  68:    */     {
/*  69:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  70:123 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  71:    */     }
/*  72:125 */     return "";
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String cargarDatos()
/*  76:    */   {
/*  77:134 */     return "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String limpiar()
/*  81:    */   {
/*  82:143 */     crearPrestamo();
/*  83:144 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public List<DetallePrestamo> getListaDetallePrestamo()
/*  87:    */   {
/*  88:149 */     List<DetallePrestamo> detalle = new ArrayList();
/*  89:150 */     for (DetallePrestamo dmc : getPrestamo().getListaDetallePrestamo()) {
/*  90:151 */       if (!dmc.isEliminado()) {
/*  91:152 */         detalle.add(dmc);
/*  92:    */       }
/*  93:    */     }
/*  94:155 */     return detalle;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String cargarEmpleado()
/*  98:    */   {
/*  99:164 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Prestamo getPrestamo()
/* 103:    */   {
/* 104:176 */     if (this.prestamo == null) {
/* 105:177 */       this.prestamo = new Prestamo();
/* 106:    */     }
/* 107:179 */     return this.prestamo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setPrestamo(Prestamo prestamo)
/* 111:    */   {
/* 112:189 */     this.prestamo = prestamo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Empleado getEmpleado()
/* 116:    */   {
/* 117:198 */     if (this.empleado == null) {
/* 118:199 */       this.empleado = new Empleado();
/* 119:    */     }
/* 120:201 */     return this.empleado;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setEmpleado(Empleado empleado)
/* 124:    */   {
/* 125:211 */     this.empleado = empleado;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.RenegociacionPrestamoBean
 * JD-Core Version:    0.7.0.1
 */