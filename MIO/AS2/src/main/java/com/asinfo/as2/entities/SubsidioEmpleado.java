/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.Date;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="subsidio_empleado")
/*  16:    */ public class SubsidioEmpleado
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 2578198954655624837L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="subsidio_empleado", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="subsidio_empleado")
/*  23:    */   @Column(name="id_subsidio_empleado")
/*  24:    */   private int idSubsidioEmpleado;
/*  25:    */   @Column(name="id_organizacion", nullable=false)
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Column(name="id_sucursal", nullable=false)
/*  28:    */   private int idSucursal;
/*  29:    */   @Column(name="fecha_desde")
/*  30:    */   private Date fechaDesde;
/*  31:    */   @Column(name="fecha_hasta")
/*  32:    */   private Date fechaHasta;
/*  33:    */   @ManyToOne
/*  34:    */   @JoinColumn(name="id_tipo_subsidio")
/*  35:    */   private TipoSubsidio tipoSubsidio;
/*  36:    */   @ManyToOne
/*  37:    */   @JoinColumn(name="id_empleado")
/*  38:    */   private Empleado empleado;
/*  39:    */   
/*  40:    */   public int getId()
/*  41:    */   {
/*  42: 90 */     return this.idSubsidioEmpleado;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getIdSubsidioEmpleado()
/*  46:    */   {
/*  47: 99 */     return this.idSubsidioEmpleado;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setIdSubsidioEmpleado(int idSubsidioEmpleado)
/*  51:    */   {
/*  52:109 */     this.idSubsidioEmpleado = idSubsidioEmpleado;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Date getFechaDesde()
/*  56:    */   {
/*  57:118 */     return this.fechaDesde;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setFechaDesde(Date fechaDesde)
/*  61:    */   {
/*  62:128 */     this.fechaDesde = fechaDesde;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Date getFechaHasta()
/*  66:    */   {
/*  67:137 */     return this.fechaHasta;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setFechaHasta(Date fechaHasta)
/*  71:    */   {
/*  72:147 */     this.fechaHasta = fechaHasta;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public TipoSubsidio getTipoSubsidio()
/*  76:    */   {
/*  77:156 */     return this.tipoSubsidio;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setTipoSubsidio(TipoSubsidio tipoSubsidio)
/*  81:    */   {
/*  82:166 */     this.tipoSubsidio = tipoSubsidio;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Empleado getEmpleado()
/*  86:    */   {
/*  87:175 */     return this.empleado;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setEmpleado(Empleado empleado)
/*  91:    */   {
/*  92:185 */     this.empleado = empleado;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getIdOrganizacion()
/*  96:    */   {
/*  97:194 */     return this.idOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdOrganizacion(int idOrganizacion)
/* 101:    */   {
/* 102:204 */     this.idOrganizacion = idOrganizacion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getIdSucursal()
/* 106:    */   {
/* 107:213 */     return this.idSucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setIdSucursal(int idSucursal)
/* 111:    */   {
/* 112:223 */     this.idSucursal = idSucursal;
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SubsidioEmpleado
 * JD-Core Version:    0.7.0.1
 */