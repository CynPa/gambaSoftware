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
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="usuario_bodega", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_usuario", "id_bodega"})})
/*  17:    */ public class UsuarioBodega
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="usuario_bodega", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="usuario_bodega")
/*  24:    */   @Column(name="id_usuario_bodega")
/*  25:    */   private int idUsuarioBodega;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @ManyToOne
/*  31:    */   @JoinColumn(name="id_usuario", nullable=true)
/*  32:    */   private EntidadUsuario entidadUsuario;
/*  33:    */   @ManyToOne
/*  34:    */   @JoinColumn(name="id_bodega", nullable=true)
/*  35:    */   private Bodega bodega;
/*  36:    */   @Column(name="predeterminado", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private boolean predeterminado;
/*  39:    */   
/*  40:    */   public int getIdUsuarioBodega()
/*  41:    */   {
/*  42: 88 */     return this.idUsuarioBodega;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setIdUsuarioBodega(int idUsuarioBodega)
/*  46:    */   {
/*  47: 98 */     this.idUsuarioBodega = idUsuarioBodega;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getIdOrganizacion()
/*  51:    */   {
/*  52:107 */     return this.idOrganizacion;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdOrganizacion(int idOrganizacion)
/*  56:    */   {
/*  57:117 */     this.idOrganizacion = idOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdSucursal()
/*  61:    */   {
/*  62:126 */     return this.idSucursal;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdSucursal(int idSucursal)
/*  66:    */   {
/*  67:136 */     this.idSucursal = idSucursal;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public EntidadUsuario getEntidadUsuario()
/*  71:    */   {
/*  72:145 */     return this.entidadUsuario;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setEntidadUsuario(EntidadUsuario entidadUsuario)
/*  76:    */   {
/*  77:155 */     this.entidadUsuario = entidadUsuario;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Bodega getBodega()
/*  81:    */   {
/*  82:164 */     return this.bodega;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setBodega(Bodega bodega)
/*  86:    */   {
/*  87:174 */     this.bodega = bodega;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isPredeterminado()
/*  91:    */   {
/*  92:183 */     return this.predeterminado;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setPredeterminado(boolean predeterminado)
/*  96:    */   {
/*  97:193 */     this.predeterminado = predeterminado;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getId()
/* 101:    */   {
/* 102:203 */     return this.idUsuarioBodega;
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.UsuarioBodega
 * JD-Core Version:    0.7.0.1
 */