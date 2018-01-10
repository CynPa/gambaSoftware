/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import org.hibernate.annotations.ColumnDefault;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="usuario_dimension_contable", uniqueConstraints={@javax.persistence.UniqueConstraint(name="UQ_usuario_dimension_contable", columnNames={"id_usuario", "id_dimension_contable", "indicador_presupuesto"})})
/*  17:    */ public class UsuarioDimensionContable
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -5896846064845442907L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="usuario_dimension_contable", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="usuario_dimension_contable")
/*  24:    */   @Column(name="id_usuario_dimension_contable")
/*  25:    */   private int idUsuarioDimensionContable;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="indicador_presupuesto", nullable=false)
/*  31:    */   @ColumnDefault("'1'")
/*  32:    */   private boolean indicadorPresupuesto;
/*  33:    */   @ManyToOne
/*  34:    */   @JoinColumn(name="id_usuario", nullable=true)
/*  35:    */   private EntidadUsuario entidadUsuario;
/*  36:    */   @ManyToOne
/*  37:    */   @JoinColumn(name="id_dimension_contable", nullable=true)
/*  38:    */   private DimensionContable dimensionContable;
/*  39:    */   
/*  40:    */   public int getId()
/*  41:    */   {
/*  42: 67 */     return this.idUsuarioDimensionContable;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getIdUsuarioDimensionContable()
/*  46:    */   {
/*  47: 71 */     return this.idUsuarioDimensionContable;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setIdUsuarioDimensionContable(int idUsuarioDimensionContable)
/*  51:    */   {
/*  52: 75 */     this.idUsuarioDimensionContable = idUsuarioDimensionContable;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdOrganizacion()
/*  56:    */   {
/*  57: 79 */     return this.idOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdOrganizacion(int idOrganizacion)
/*  61:    */   {
/*  62: 83 */     this.idOrganizacion = idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public EntidadUsuario getEntidadUsuario()
/*  66:    */   {
/*  67: 87 */     return this.entidadUsuario;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setEntidadUsuario(EntidadUsuario entidadUsuario)
/*  71:    */   {
/*  72: 91 */     this.entidadUsuario = entidadUsuario;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public DimensionContable getDimensionContable()
/*  76:    */   {
/*  77: 95 */     return this.dimensionContable;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setDimensionContable(DimensionContable dimensionContable)
/*  81:    */   {
/*  82: 99 */     this.dimensionContable = dimensionContable;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdSucursal()
/*  86:    */   {
/*  87:103 */     return this.idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdSucursal(int idSucursal)
/*  91:    */   {
/*  92:107 */     this.idSucursal = idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean isIndicadorPresupuesto()
/*  96:    */   {
/*  97:111 */     return this.indicadorPresupuesto;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIndicadorPresupuesto(boolean indicadorPresupuesto)
/* 101:    */   {
/* 102:115 */     this.indicadorPresupuesto = indicadorPresupuesto;
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.UsuarioDimensionContable
 * JD-Core Version:    0.7.0.1
 */