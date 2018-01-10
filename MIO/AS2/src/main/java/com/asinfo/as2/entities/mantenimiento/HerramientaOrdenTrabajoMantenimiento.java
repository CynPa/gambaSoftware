/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.Digits;
/*  17:    */ import javax.validation.constraints.Min;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="herramienta_orden_trabajo_mantenimiento")
/*  22:    */ public class HerramientaOrdenTrabajoMantenimiento
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="herramienta_orden_trabajo_mantenimiento", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="herramienta_orden_trabajo_mantenimiento")
/*  30:    */   @Column(name="id_herramienta_orden_trabajo_mantenimiento")
/*  31:    */   private int idHerramientaOrdenTrabajoMantenimiento;
/*  32:    */   @Column(name="id_organizacion")
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal")
/*  35:    */   private int idSucursal;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_orden_trabajo_mantenimiento", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_herramienta", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private Herramienta herramienta;
/*  44:    */   @Column(name="cantidad_requerida", nullable=false, precision=12, scale=2)
/*  45:    */   @Digits(integer=10, fraction=2)
/*  46:    */   @Min(0L)
/*  47:    */   @NotNull
/*  48: 60 */   private BigDecimal cantidadRequerida = BigDecimal.ZERO;
/*  49:    */   
/*  50:    */   public int getId()
/*  51:    */   {
/*  52: 76 */     return this.idHerramientaOrdenTrabajoMantenimiento;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdHerramientaOrdenTrabajoMantenimiento()
/*  56:    */   {
/*  57: 80 */     return this.idHerramientaOrdenTrabajoMantenimiento;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdHerramientaOrdenTrabajoMantenimiento(int idHerramientaOrdenTrabajoMantenimiento)
/*  61:    */   {
/*  62: 84 */     this.idHerramientaOrdenTrabajoMantenimiento = idHerramientaOrdenTrabajoMantenimiento;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdOrganizacion()
/*  66:    */   {
/*  67: 88 */     return this.idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdOrganizacion(int idOrganizacion)
/*  71:    */   {
/*  72: 92 */     this.idOrganizacion = idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdSucursal()
/*  76:    */   {
/*  77: 96 */     return this.idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdSucursal(int idSucursal)
/*  81:    */   {
/*  82:100 */     this.idSucursal = idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/*  86:    */   {
/*  87:104 */     return this.ordenTrabajoMantenimiento;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/*  91:    */   {
/*  92:108 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Herramienta getHerramienta()
/*  96:    */   {
/*  97:112 */     return this.herramienta;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setHerramienta(Herramienta herramienta)
/* 101:    */   {
/* 102:116 */     this.herramienta = herramienta;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public BigDecimal getCantidadRequerida()
/* 106:    */   {
/* 107:120 */     return this.cantidadRequerida;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setCantidadRequerida(BigDecimal cantidadRequerida)
/* 111:    */   {
/* 112:124 */     this.cantidadRequerida = cantidadRequerida;
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.HerramientaOrdenTrabajoMantenimiento
 * JD-Core Version:    0.7.0.1
 */