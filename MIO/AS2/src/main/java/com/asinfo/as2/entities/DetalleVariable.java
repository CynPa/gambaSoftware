/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="detalle_variable", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_variable", "id_cuenta_contable"})})
/*  18:    */ public class DetalleVariable
/*  19:    */   extends EntidadBase
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="detalle_variable", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_variable")
/*  26:    */   @Column(name="id_detalle_variable")
/*  27:    */   private int idDetalleVariable;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   @NotNull
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   @NotNull
/*  33:    */   private int idSucursal;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_variable", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private Variable variable;
/*  38:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  39:    */   @JoinColumn(name="id_cuenta_contable", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private CuentaContable cuentaContable;
/*  42:    */   
/*  43:    */   public int getId()
/*  44:    */   {
/*  45: 78 */     return getIdDetalleVariable();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getIdDetalleVariable()
/*  49:    */   {
/*  50: 82 */     return this.idDetalleVariable;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setIdDetalleVariable(int idDetalleVariable)
/*  54:    */   {
/*  55: 86 */     this.idDetalleVariable = idDetalleVariable;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdSucursal()
/*  59:    */   {
/*  60: 90 */     return this.idSucursal;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdSucursal(int idSucursal)
/*  64:    */   {
/*  65: 94 */     this.idSucursal = idSucursal;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70: 98 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75:102 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Variable getVariable()
/*  79:    */   {
/*  80:106 */     return this.variable;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setVariable(Variable variable)
/*  84:    */   {
/*  85:111 */     this.variable = variable;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public CuentaContable getCuentaContable()
/*  89:    */   {
/*  90:116 */     return this.cuentaContable;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setCuentaContable(CuentaContable cuentaContable)
/*  94:    */   {
/*  95:121 */     this.cuentaContable = cuentaContable;
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleVariable
 * JD-Core Version:    0.7.0.1
 */